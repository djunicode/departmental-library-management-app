from django.db import models
from django.contrib.auth.models import AbstractUser


# Create your models here.


class User(AbstractUser):
    email = models.EmailField(max_length=50, unique=True)
    username = models.CharField(max_length=25)
    is_librarian = models.BooleanField(default=False)
    is_teacher = models.BooleanField(default=False)
    is_student = models.BooleanField(default=False)

    USERNAME_FIELD = "email"

    REQUIRED_FIELDS = ["username", "first_name", "last_name"]


class Librarian(User):
    phone = models.CharField(max_length=10)
    address = models.TextField()
    sap_id = models.CharField(max_length=11, primary_key=True)
    joined_on = models.DateField()

    USERNAME_FIELD = "sap_id"

    REQUIRED_FIELDS = [
        "username",
        "first_name",
        "last_name",
        "phone",
        "address",
        "joined_on",
    ]

    def __str__(self):
        return self.first_name + " " + self.last_name

    class Meta:
        verbose_name_plural = "Librarian"


class Teacher(User):
    phone = models.CharField(max_length=10)
    address = models.TextField()
    sap_id = models.CharField(max_length=11, primary_key=True)
    department = models.CharField(max_length=10)

    USERNAME_FIELD = "sap_id"

    REQUIRED_FIELDS = [
        "username",
        "first_name",
        "last_name",
        "username",
        "phone",
        "address",
        "department",
    ]

    def __str__(self):
        return self.first_name + " " + self.last_name

    class Meta:
        verbose_name = "Teacher"


class Student(User):
    phone = models.CharField(max_length=10)
    address = models.TextField()
    sap_id = models.CharField(max_length=11, primary_key=True)
    department = models.CharField(max_length=10)
    grad_year = models.CharField(max_length=4)

    USERNAME_FIELD = "sap_id"

    REQUIRED_FIELDS = [
        "username",
        "first_name",
        "last_name",
        "username",
        "phone",
        "address",
        "department",
        "grad_year",
    ]

    def __str__(self):
        return self.first_name + " " + self.last_name

    class Meta:
        verbose_name = "Student"


class Book(models.Model):
    book_id = models.CharField(max_length=30)
    name = models.CharField(max_length=30)
    publisher = models.CharField(max_length=30)
    author = models.CharField(max_length=30)
    publish_year = models.CharField(max_length=4)
    quantity = models.IntegerField()
    demand = models.BigIntegerField()
    is_available = models.BooleanField(default=True)

    def __str__(self):
        return self.name + "_" + self.author


class WaitingList(models.Model):

    book = models.ForeignKey(Book, on_delete=models.CASCADE)
    teacher = models.ForeignKey(
        Teacher, on_delete=models.CASCADE, blank=True, null=True
    )
    student = models.ForeignKey(
        Student, on_delete=models.CASCADE, blank=True, null=True
    )
    is_alerted = models.BooleanField(default=False)
    is_collected = models.BooleanField(default=False)
    alerted_on = models.DateTimeField()

    def __str__(self):
        return self.book.name + "_" + str(self.id)

    class Meta:
        verbose_name_plural = "Waiting List"


class Penalty(models.Model):
    book = models.ForeignKey(Book, on_delete=models.CASCADE)
    student = models.ForeignKey(Student, on_delete=models.CASCADE)
    issue_date = models.DateField()
    return_date = models.DateField()
    fine = models.IntegerField(default=0)

    def __str__(self):
        return self.book.name + "_" + self.student.username + "_" + str(self.id)

    class Meta:
        verbose_name_plural = "Penalties"


class Notification(models.Model):
    penalty = models.ForeignKey(Penalty, on_delete=models.CASCADE)
    waiting = models.ForeignKey(WaitingList, on_delete=models.CASCADE)
    notification = models.CharField(max_length=50)
