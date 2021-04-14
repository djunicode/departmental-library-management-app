from rest_framework import generics, permissions, viewsets
from rest_framework.response import Response
from rest_framework.permissions import AllowAny
from .models import *

from rest_framework.views import APIView
from rest_framework.generics import CreateAPIView
from .serializers import *
from .permission import *

from django.contrib.auth import login
from django.shortcuts import get_object_or_404
from rest_framework import permissions
from django.contrib.auth import authenticate
from django.views.decorators.csrf import csrf_exempt
from rest_framework.authtoken.models import Token
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from rest_framework.status import HTTP_400_BAD_REQUEST, HTTP_404_NOT_FOUND, HTTP_200_OK

from rest_framework.authtoken.views import ObtainAuthToken
from rest_framework.authtoken.models import Token
from django.db.models import Q
from datetime import date
from rest_framework.permissions import IsAuthenticated
from django.utils import timezone


class RegisterUser(generics.GenericAPIView):
    serializer_class = RegisterSerializer1
    queryset = User.objects.all()

    def get(self, request):
        users = User.objects.all()
        serializer = RegisterSerializer1(users, many=True)
        return Response(serializer.data)

    def post(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        user = serializer.save()
        if user.is_student:
            role = "STUDENT"
        elif user.is_teacher:
            role = "TEACHER"
        elif user.is_librarian:
            role = "LIBRARIAN"
        else:
            role = "DEFAULT"
        token, _ = Token.objects.get_or_create(user=user)
        return Response({"role": role, "token": token.key}, status=HTTP_200_OK)

        # return Response(
        #    {
        #        "user": UserSerializer(                                ## DISPLAYING TOKEN INSTEAD OF FULL USER DETAILS
        #            user, context=self.get_serializer_context()
        #        ).data,
        #        "token": AuthToken.objects.create(user)[1],
        #    }
        # )


class RegisterStudent(generics.GenericAPIView):
    serializer_class = StudentRegisterSerializer1
    queryset = Student.objects.all()

    def get(self, request):
        users = Student.objects.all()
        serializer = StudentRegisterSerializer1(users, many=True)
        return Response(serializer.data)

    def post(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        user = serializer.save()
        if user.is_student:
            role = "STUDENT"
        elif user.is_teacher:
            role = "TEACHER"
        elif user.is_librarian:
            role = "LIBRARIAN"
        else:
            role = "DEFAULT"
        token, _ = Token.objects.get_or_create(user=user)
        return Response({"role": role, "token": token.key}, status=HTTP_200_OK)
        # return Response({
        # "user": StudentDisplaySerializer(user, context=self.get_serializer_context()).data,
        # "token": AuthToken.objects.create(user)[1]
        # })


class RegisterTeacher(generics.GenericAPIView):
    serializer_class = TeacherRegisterSerializer1
    queryset = Teacher.objects.all()

    def get(self, request):
        users = Teacher.objects.all()
        serializer = TeacherRegisterSerializer1(users, many=True)
        return Response(serializer.data)

    def post(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        user = serializer.save()
        if user.is_student:
            role = "STUDENT"
        elif user.is_teacher:
            role = "TEACHER"
        elif user.is_librarian:
            role = "LIBRARIAN"
        else:
            role = "DEFAULT"
        token, _ = Token.objects.get_or_create(user=user)
        return Response({"role": role, "token": token.key}, status=HTTP_200_OK)


class RegisterLibrarian(generics.GenericAPIView):
    serializer_class = LibrarianRegisterSerializer1
    queryset = Librarian.objects.all()

    def get(self, request):
        users = Librarian.objects.all()
        serializer = LibrarianRegisterSerializer1(users, many=True)
        return Response(serializer.data)

    def post(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        user = serializer.save()
        if user.is_student:
            role = "STUDENT"
        elif user.is_teacher:
            role = "TEACHER"
        elif user.is_librarian:
            role = "LIBRARIAN"
        else:
            role = "DEFAULT"
        token, _ = Token.objects.get_or_create(user=user)
        return Response({"role": role, "token": token.key}, status=HTTP_200_OK)


class CustomAuthToken(ObtainAuthToken):
    def post(self, request, *args, **kwargs):
        serializer = self.serializer_class(
            data=request.data, context={"request": request}
        )
        serializer.is_valid(raise_exception=True)
        user = serializer.validated_data["user"]
        token, created = Token.objects.get_or_create(user=user)
        if user.is_student:
            role = "STUDENT"
        elif user.is_teacher:
            role = "TEACHER"
        elif user.is_librarian:
            role = "LIBRARIAN"
        else:
            role = "DEFAULT"
        return Response(
            {
                "token": token.key,
                "role": role,
            }
        )


class SubjectFilter(generics.GenericAPIView):
    queryset = Book.objects.all()
    serializer_class = BookSerializer
    permission_classes = (IsAuthenticated,)

    def get(self, request, subject):
        book = Book.objects.filter(Q(subject__istartswith=subject))
        for i in book:
            print(i.available_quantity)
        serializer = BookSerializer(book, many=True)
        return Response(serializer.data)


class SearchOption(generics.GenericAPIView):
    queryset = Book.objects.all()
    serializer_class = SearchOptionSerializer
    permission_classes = (IsAuthenticated,)

    def post(self, request):
        lst = []
        title = request.data.get("title")
        title = title.split(" ")
        for j in title:
            book = Book.objects.filter(Q(name__istartswith=j)).distinct()
            serializer = BookSerializer(book, many=True)
            return Response(serializer.data)


class AscSort(generics.GenericAPIView):
    queryset = Book.objects.all()
    serializer_class = BookSerializer
    permission_classes = (IsAuthenticated,)

    def get(self, request):

        book = Book.objects.all().order_by("name")
        serializer = BookSerializer(book, many=True)
        return Response(serializer.data)


class DescSort(generics.GenericAPIView):
    queryset = Book.objects.all()
    serializer_class = BookSerializer
    permission_classes = (IsAuthenticated,)

    def get(self, request):

        book = Book.objects.all().order_by("-name")
        serializer = BookSerializer(book, many=True)
        return Response(serializer.data)


class WaitingListDetails(generics.GenericAPIView):
    queryset = WaitingList.objects.all()
    serializer_class = WaitingListSerializer
    permission_classes = (
        IsAuthenticated,
        IsLibrarian,
    )

    def get(self, request):
        lst = []
        waiting = WaitingList.objects.all()
        if request.user.is_teacher:
            for i in waiting:
                data = {
                    "id": i.id,
                    "name": i.teacher.user.email,
                    "alerted": i.is_alerted,
                    "book": i.book.name,
                }
                lst.append(data)
        else:
            for i in waiting:
                data = {
                    "id": i.id,
                    "name": i.student.user.email,
                    "alerted": i.is_alerted,
                    "book": i.book.name,
                }
                lst.append(data)

        return Response(lst)


class BooksAdd(generics.GenericAPIView):

    serializer_class = BookSerializer
    queryset = Book.objects.all()
    permission_classes = (IsAuthenticated, IsLibrarian)

    def post(self, request):

        serializer = BookSerializer(data=request.data)
        print(request.data.get("isbn"))
        if Book.objects.filter(isbn=request.data.get("isbn")).exists():
            lst = []
            book = Book.objects.get(isbn=request.data.get("isbn"))
            copy_item = Copy.objects.all().order_by("-id")[0].barcode
            barcode = int(copy_item) + 1
            copy = Copy.objects.create(barcode=barcode, book=book, condition="BEST")
            copy.save()
            data = {
                "id": book.id,
                "name": book.name,
                "publisher": book.publisher,
                "author": book.author,
                "publish_year": book.publish_year,
                "quantity": book.available_quantity,
                "subject": book.subject,
            }
            lst.append(data)

            return Response(lst)
        if serializer.is_valid():
            serializer.save()
            book = Books.objects.get(isbn=request.data.get("isbn"))
            copy_item = Copy.objects.all().order_by("-id")[0].barcode
            barcode = int(copy_item) + 1
            copy = Copy.objects.create(barcode=barcode, book=book, condition="BEST")
            return serializer.data

    def get(self, request):
        book = Book.objects.all()
        serializer = BookSerializer(book, many=True)
        return Response(serializer.data)


class CopyBookAll(generics.GenericAPIView):
    serializer_class = UpdateBookAllSerializer
    queryset = Book.objects.all()
    permission_classes = (IsAuthenticated, IsLibrarian)

    def get(self, request, id):
        book = Book.objects.get(id=id)
        copy = Copy.objects.filter(book=book)
        lst = []
        for i in copy:
            data = {
                "id": i.id,
                "name": i.book.name,
                "condition": i.condition,
                "is_available": i.is_available,
            }
            lst.append(data)

        return Response(lst)


class BookDetail(generics.GenericAPIView):
    serializer_class = UpdateBookAllSerializer
    queryset = Book.objects.all()
    permission_classes = (IsAuthenticated, IsLibrarian)

    def get(self, request, id, book):
        try:
            copy = Copy.objects.get(id=book)
        except:
            return Response("Book not found", status=HTTP_400_BAD_REQUEST)
        issue = Issue.objects.filter(copy=copy)
        lst_issue = []
        lst = []
        for i in issue:
            if i.teacher == None:
                data = {
                    "id": i.id,
                    "student": i.student.sap_id,
                    "issue_date": i.issue_date,
                    "return_date": i.return_date,
                    "fine": i.fine,
                    "paid": i.paid,
                }
                lst_issue.append(data)
            else:
                data = {
                    "id": i.id,
                    "teacher": i.teacher.sap_id,
                    "issue_date": i.issue_date,
                    "return_date": i.return_date,
                    "fine": i.fine,
                    "paid": i.paid,
                }
                lst_issue.append(data)
        lst_issue = lst_issue[::-1]
        data1 = {
            "id": copy.id,
            "name": copy.book.name,
            "condition": copy.condition,
            "is_available": copy.is_available,
            "issues": lst_issue,
        }
        lst.append(data1)
        return Response(lst)

    def put(self, request, id, book):
        try:
            copy = Copy.objects.get(id=book)
        except:
            return Response("Book not found", status=HTTP_400_BAD_REQUEST)
        condition = request.data.get("condition")
        copy.condition = condition
        copy.save()
        data = {
            "id": copy.id,
            "barcode": copy.barcode,
            "name": copy.book.name,
            "condition": copy.condition,
        }
        lst = []
        lst.append(data)
        return Response(lst, status=HTTP_200_OK)

    def delete(self, request, id, book):
        try:
            copy = Copy.objects.get(id=book)
        except:
            return Response("Book not found", status=HTTP_400_BAD_REQUEST)
        copy.delete()
        return Response("deleted successfully", status=HTTP_200_OK)


class BookRequestView(generics.GenericAPIView):
    serializer_class = WaitingListSerializer
    permission_classes = [IsAuthenticated, IsTeacherStudent]

    def get(self, request, book_id):
        if Book.objects.filter(id=book_id):
            book = Book.objects.get(id=book_id)
            if book.available_quantity > 0:
                copies = Copy.objects.filter(book=book)
                copy = Copy.objects.filter(is_available=True, book=book)
                copy = copy[0]
                if request.user.is_student:
                    student = Student.objects.get(email=request.user)
                    if not Issue.objects.filter(copy__in=copies, student=student):
                        WaitingList.objects.create(
                            book=book,
                            student=student,
                            is_alerted=True,
                            alerted_on=timezone.now(),
                        )
                        issue = Issue.objects.create(copy=copy, student=student)
                        Notification.objects.create(
                            nf_type="ALLOTED",
                            user=student,
                            notification="You are allocated the book "
                            + book.name
                            + ". Please collect it from the college library in 2 days",
                        )
                    else:
                        return Response(
                            "You have already issued the book : " + book.name
                        )
                else:
                    teacher = Teacher.objects.get(email=request.user)
                    if not Issue.objects.filter(copy__in=copies, teacher=teacher):
                        WaitingList.objects.create(
                            book=book,
                            teacher=teacher,
                            is_alerted=True,
                            alerted_on=datetime.date.today(),
                        )
                        issue = Issue.objects.create(copy=copy, teacher=teacher)
                        Notification.objects.create(
                            nf_type="ALLOTED",
                            user=teacher,
                            notification="You are allocated the book "
                            + book.name
                            + ". Please collect it from the college library in 5 days",
                        )
                    else:
                        return Response(
                            "You have already issued the book : " + book.name
                        )
                copy.is_available = False
                copy.save()
                return Response(
                    "You are allocated the book "
                    + book.name
                    + ". Please collect it from the college library. Your issue id is : "
                    + str(issue.id)
                )
            else:
                if request.user.is_student and not WaitingList.objects.filter(
                    book=book, student=request.user
                ):
                    student = Student.objects.get(email=request.user)
                    WaitingList.objects.create(book=book, student=student)
                elif request.user.is_teacher and not WaitingList.objects.filter(
                    book=book, teacher=request.user
                ):
                    teacher = Teacher.objects.get(email=request.user)
                    WaitingList.objects.create(book=book, teacher=teacher)
                else:
                    return Response(
                        "You are already there in the waiting list for this book"
                    )
                return Response(
                    "Book is not available currently, you'll be notified once it's available"
                )
        else:
            return Response("Book not found", status=HTTP_400_BAD_REQUEST)


class IssuedViewSet(viewsets.ModelViewSet):
    serializer_class = IssuedSerializer

    def get_queryset(self):
        if self.request.user.is_student:
            return Issue.objects.filter(student=self.request.user)
        else:
            return Issue.objects.filter(teacher=self.request.user)

    def get_permissions(self):
        if self.request.method in permissions.SAFE_METHODS:
            return (IsAuthenticated(),)
        else:
            return (IsAuthenticated(), IsLibrarian())


class LibrarianIssueViewSet(viewsets.ModelViewSet):
    serializer_class = LibrarianIssueSerializer
    queryset = Issue.objects.all()
    permission_classes = [IsAuthenticated, IsLibrarian]


class NotificationView(generics.GenericAPIView):
    queryset = Notification.objects.all()

    def get(self, request, nf_type):
        nf = nf_type.upper()
        return Response(list(Notification.objects.filter(nf_type=nf).values()))
