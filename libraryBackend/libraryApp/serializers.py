from django.contrib.auth import authenticate

from .models import *
from rest_framework import serializers
import datetime
from django.utils import timezone

# Register Serializer
class RegisterSerializer1(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ("id", "email", "first_name", "last_name", "password")
        extra_kwargs = {"password": {"write_only": True}}

    def create(self, validated_data):
        user = User.objects.create_user(
            email=validated_data["email"],
            password=validated_data["password"],
            last_name=validated_data["last_name"],
            first_name=validated_data["first_name"],
        )

        return user


class StudentRegisterSerializer1(serializers.ModelSerializer):
    class Meta:
        model = Student
        fields = (
            "id",
            "email",
            "first_name",
            "last_name",
            "sap_id",
            "department",
            "graduation_year",
            "password",
        )
        extra_kwargs = {"password": {"write_only": True}}

    def create(self, validated_data):
        user = Student.objects.create_user(
            email=validated_data["email"],
            sap_id=validated_data["sap_id"],
            password=validated_data["password"],
            department=validated_data["department"],
            graduation_year=validated_data["graduation_year"],
            is_student=True,
            is_teacher=False,
            is_librarian=False,
            last_name=validated_data["last_name"],
            first_name=validated_data["first_name"],
        )

        return user


class TeacherRegisterSerializer1(serializers.ModelSerializer):
    class Meta:
        model = Teacher
        fields = (
            "id",
            "email",
            "department",
            "sap_id",
            "first_name",
            "last_name",
            "password",
        )
        extra_kwargs = {"password": {"write_only": True}}

    def create(self, validated_data):
        user = Teacher.objects.create_user(
            email=validated_data["email"],
            password=validated_data["password"],
            department=validated_data["department"],
            sap_id=validated_data["sap_id"],
            is_student=False,
            is_teacher=True,
            is_librarian=False,
            last_name=validated_data["last_name"],
            first_name=validated_data["first_name"],
        )

        return user


class LibrarianRegisterSerializer1(serializers.ModelSerializer):
    class Meta:
        model = Librarian
        fields = (
            "id",
            "email",
            "first_name",
            "last_name",
            "joined_on",
            "librarian_id",
            "password",
        )
        extra_kwargs = {"password": {"write_only": True}}

    def create(self, validated_data):
        user = Librarian.objects.create_user(
            email=validated_data["email"],
            password=validated_data["password"],
            is_student=False,
            is_teacher=False,
            is_librarian=True,
            joined_on=validated_data["joined_on"],
            librarian_id=validated_data["librarian_id"],
            last_name=validated_data["last_name"],
            first_name=validated_data["first_name"],
        )

        return user


class BookSerializer(serializers.ModelSerializer):
    class Meta:
        model = Book
        fields = "__all__"


class SearchOptionSerializer(serializers.Serializer):
    title = serializers.CharField(max_length=200)


class WaitingListSerializer(serializers.ModelSerializer):
    class Meta:
        model = WaitingList
        fields = "__all__"


class IssueSerializer(serializers.Serializer):
    issue = serializers.CharField(max_length=200)


class BookIssuedSerializer(serializers.ModelSerializer):
    class Meta:
        model = Issue
        fields = "__all__"


class UpdateBookAllSerializer(serializers.ModelSerializer):
    class Meta:
        model = Copy
        fields = ["condition"]


class LibrarianIssueSerializer(serializers.ModelSerializer):
    class Meta:
        model = Issue
        fields = "__all__"
        read_only_fields = ("fine",)

    def update(self, instance, validated_data):
        copy = validated_data["copy"]
        book = copy.book
        if not instance.return_date and validated_data["return_date"]:

            if WaitingList.objects.filter(book=book, is_alerted=False):
                if WaitingList.objects.filter(
                    book=book, is_alerted=False, student__isnull=True
                ):
                    items = WaitingList.objects.filter(
                        book=book, is_alerted=False, student__isnull=True
                    ).order_by("id")
                    item = items[0]
                    Issue.objects.create(copy=copy, teacher=item.teacher)
                    Notification.objects.create(
                        nf_type="ALLOTED",
                        user=item.teacher,
                        notification="You are allocated the book "
                        + book.name
                        + ". Please collect it from the college library in 2 days",
                    )

                else:
                    items = WaitingList.objects.filter(
                        book=book, is_alerted=False
                    ).order_by("id")
                    item = items[0]
                    Issue.objects.create(copy=copy, student=item.student)
                    Notification.objects.create(
                        nf_type="ALLOTED",
                        user=item.student,
                        notification="You are allocated the book "
                        + book.name
                        + ".Please collect it from the college library in 5 days",
                    )
                item.is_alerted = True
                item.alerted_on = timezone.now()
                item.save()
            else:
                copy.is_available = True
                copy.save()
            instance.return_date = validated_data.get(
                "return_date", instance.return_date
            )
            instance.save()
        elif not instance.issue_date and validated_data["issue_date"]:
            if instance.student:
                WaitingList.objects.filter(
                    is_alerted=True, book=book, student=instance.student
                ).delete()
                Notification.objects.create(
                    nf_type="COLLECTED",
                    user=instance.student,
                    notification="You have collected the book "
                    + book.name
                    + " from the college library on "
                    + str(validated_data["issue_date"]),
                )
            else:
                WaitingList.objects.filter(
                    is_alerted=True, book=book, teacher=instance.teacher
                ).delete()
                Notification.objects.create(
                    nf_type="COLLECTED",
                    user=instance.teacher,
                    notification="You have collected the book "
                    + book.name
                    + " from the college library on "
                    + str(validated_data["issue_date"]),
                )

            instance.issue_date = validated_data.get("issue_date", instance.issue_date)
            instance.save()
        return instance


class IssuedSerializer(serializers.ModelSerializer):
    class Meta:
        model = Issue
        fields = "__all__"

class CheckBookExistsSerializer(serializers.Serializer):
    isbn = serializers.CharField(max_length=200)

class AddNCopiesBooksSerializer(serializers.Serializer):
    isbn = serializers.CharField(max_length=200)
    copies = serializers.CharField(max_length=200)

    def validate(self,data):
        copies=data['copies']
        try:
            copies=int(copies)
        except:
            raise serializers.ValidationError("please enter a valid number")
        return data