from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from django.contrib.auth.models import Group
from django import forms
from .models import (
    User,
    Student,
    Teacher,
    Librarian,
    Book,
    Copy,
    WaitingList,
    Issue,
    Notification,
)


class UserCreationForm(forms.ModelForm):
    class Meta:
        model = User
        fields = ("email",)

    def save(self, commit=True):
        # Save the provided password in hashed format
        user = super(UserCreationForm, self).save(commit=False)
        user.set_password(self.cleaned_data["password"])
        if commit:
            user.save()
        return user


class StudentCreationForm(forms.ModelForm):
    class Meta:
        model = Student
        fields = ("email",)

    def save(self, commit=True):
        # Save the provided password in hashed format
        user = super(StudentCreationForm, self).save(commit=False)
        user.set_password(self.cleaned_data["password"])
        if commit:
            user.save()
        return user


class TeacherCreationForm(forms.ModelForm):
    class Meta:
        model = Teacher
        fields = ("email",)

    def save(self, commit=True):
        # Save the provided password in hashed format
        user = super(TeacherCreationForm, self).save(commit=False)
        user.set_password(self.cleaned_data["password"])
        if commit:
            user.save()
        return user


class LibrarianCreationForm(forms.ModelForm):
    class Meta:
        model = Librarian
        fields = ("email",)

    def save(self, commit=True):
        # Save the provided password in hashed format
        user = super(LibrarianCreationForm, self).save(commit=False)
        user.set_password(self.cleaned_data["password"])
        if commit:
            user.save()
        return user


class CustomUserAdmin(UserAdmin):
    # The forms to add and change user instances
    add_form = UserCreationForm
    list_display = ("email", "is_student", "is_teacher", "is_librarian", "is_superuser")
    ordering = ("email",)

    fieldsets = ((None, {"fields": ("email", "password", "first_name", "last_name")}),)
    add_fieldsets = (
        (
            None,
            {
                "classes": ("wide",),
                "fields": (
                    "email",
                    "password",
                    "first_name",
                    "last_name",
                    "mobile",
                    "address",
                    "is_student",
                    "is_teacher",
                    "is_librarian",
                    "is_admin",
                    "is_superuser",
                    "is_staff",
                    "is_active",
                ),
            },
        ),
    )

    filter_horizontal = ()


class CustomStudentAdmin(UserAdmin):
    # The forms to add and change user instances
    add_form = StudentCreationForm
    list_display = ("email", "sap_id", "department", "first_name", "last_name")
    ordering = ("email",)

    fieldsets = ((None, {"fields": ("email", "password", "first_name", "last_name")}),)
    add_fieldsets = (
        (
            None,
            {
                "classes": ("wide",),
                "fields": (
                    "email",
                    "password",
                    "first_name",
                    "last_name",
                    "mobile",
                    "address",
                    "sap_id",
                    "department",
                    "graduation_year",
                    "is_student",
                    "is_teacher",
                    "is_librarian",
                    "is_admin",
                    "is_superuser",
                    "is_staff",
                    "is_active",
                ),
            },
        ),
    )

    filter_horizontal = ()


class CustomTeacherAdmin(UserAdmin):
    # The forms to add and change user instances
    add_form = TeacherCreationForm
    list_display = ("email", "sap_id", "department", "first_name", "last_name")
    ordering = ("email",)

    fieldsets = ((None, {"fields": ("email", "password", "first_name", "last_name")}),)
    add_fieldsets = (
        (
            None,
            {
                "classes": ("wide",),
                "fields": (
                    "email",
                    "password",
                    "first_name",
                    "last_name",
                    "mobile",
                    "address",
                    "sap_id",
                    "department",
                    "is_student",
                    "is_teacher",
                    "is_librarian",
                    "is_admin",
                    "is_superuser",
                    "is_staff",
                    "is_active",
                ),
            },
        ),
    )

    filter_horizontal = ()


class CustomLibrarianAdmin(UserAdmin):
    # The forms to add and change user instances
    add_form = LibrarianCreationForm
    list_display = ("email", "librarian_id", "joined_on", "first_name", "last_name")
    ordering = ("email",)

    fieldsets = ((None, {"fields": ("email", "password", "first_name", "last_name")}),)
    add_fieldsets = (
        (
            None,
            {
                "classes": ("wide",),
                "fields": (
                    "email",
                    "password",
                    "first_name",
                    "last_name",
                    "mobile",
                    "address",
                    "joined_on",
                    "librarian_id",
                    "is_student",
                    "is_teacher",
                    "is_librarian",
                    "is_admin",
                    "is_superuser",
                    "is_staff",
                    "is_active",
                ),
            },
        ),
    )

    filter_horizontal = ()


admin.site.unregister(Group)
admin.site.register(User, CustomUserAdmin)
admin.site.register(Student, CustomStudentAdmin)
admin.site.register(Teacher, CustomTeacherAdmin)
admin.site.register(Librarian, CustomLibrarianAdmin)
admin.site.register((Book, Copy, WaitingList, Issue, Notification))
