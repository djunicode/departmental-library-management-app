from django.db import models
from django.contrib.auth.models import AbstractBaseUser, BaseUserManager
from django.contrib.auth.models import PermissionsMixin
from django.utils.translation import ugettext_lazy as _
from .managers import UserManager, StudentManager, TeacherManager, LibrarianManager
from django.core.validators import RegexValidator

# Create your models here.


class User(AbstractBaseUser, PermissionsMixin):
    email = models.EmailField(_("email address"), unique=True)
    first_name = models.CharField(_("first name"), max_length=40, blank=True)
    last_name = models.CharField(_("last name"), max_length=40, blank=True)
    mobile = models.CharField(_("mobile"), max_length=13, blank=True)
    address = models.CharField(_("address"), max_length=255, blank=True)
    # date_joined = models.DateTimeField(_('date joined'), auto_now_add = True)
    is_active = models.BooleanField(_("active"), default=True)
    is_staff = models.BooleanField(_("staff status"), default=False)
    is_superuser = models.BooleanField(_("is superuser"), default=False)
    is_admin = models.BooleanField(_("is admin"), default=False)
    is_student = models.BooleanField(_("is student"), default=False)
    is_teacher = models.BooleanField(_("is teacher"), default=False)
    is_librarian = models.BooleanField(_("is librarian"), default=False)
    objects = UserManager()

    USERNAME_FIELD = "email"
    REQUIRED_FIELDS = []

    class Meta:
        verbose_name = _("user")
        verbose_name_plural = _("users")

    def get_short_name(self):
        return self.first_name

    def get_full_name(self):
        return self.first_name + "_" + self.last_name

    def save(self, *args, **kwargs):

        self.username = self.email
        super(User, self).save(*args, **kwargs)

    def __str__(self):

        return self.email


class Student(User):
    user = models.OneToOneField(User, on_delete=models.CASCADE, parent_link=True)
    user.is_student = True
    user.is_teacher = False
    user.is_librarian = False

    DEPT_CHOICES = departments = (
        ("CS", "COMPUTERS"),
        ("IT", "INFORMATION TECHNOLOGY"),
        ("EXTC", "ELECTRONICS AND TELECOMMUNICATION"),
        ("ELEX", "ELECTRONICS"),
        ("MECH", "MECHANICAL"),
        ("CHEM", "CHEMICAL"),
        ("BIOMED", "BIOMEDICAL"),
        ("PROD", "PRODUCTION"),
        ("OTHERS", "OTHERS"),
    )
    department = models.CharField(choices=DEPT_CHOICES, max_length=40)

    sap_regex = RegexValidator(
        regex=r"^\+?6?\d{10,12}$", message="SAP ID must be valid"
    )

    sap_id = models.CharField(
        validators=[sap_regex],
        max_length=12,
        blank=False,
        null=False,
        default=None,
        unique=True,
    )

    GRADUATION_YEAR_CHOICES = (
        ("2021", 2021),
        ("2022", 2022),
        ("2023", 2023),
        ("2024", 2024),
        ("2025", 2025),
    )
    graduation_year = models.CharField(
        max_length=4, blank=False, choices=GRADUATION_YEAR_CHOICES
    )
    objects = StudentManager()

    USERNAME_FIELD = "email"
    REQUIRED_FIELDS = ["sap_id", "graduation_year", "department"]

    def __str__(self):
        return self.user.email


class Teacher(User):

    user = models.OneToOneField(User, on_delete=models.CASCADE, parent_link=True)
    user.is_student = False
    user.is_teacher = True
    user.is_librarian = False

    DEPT_CHOICES = departments = (
        ("CS", "COMPUTERS"),
        ("IT", "INFORMATION TECHNOLOGY"),
        ("EXTC", "ELECTRONICS AND TELECOMMUNICATION"),
        ("ELEX", "ELECTRONICS"),
        ("MECH", "MECHANICAL"),
        ("CHEM", "CHEMICAL"),
        ("BIOMED", "BIOMEDICAL"),
        ("PROD", "PRODUCTION"),
        ("OTHERS", "OTHERS"),
    )
    department = models.CharField(choices=DEPT_CHOICES, max_length=40)

    sap_regex = RegexValidator(
        regex=r"^\+?6?\d{10,12}$", message="SAP ID must be valid"
    )
    sap_id = models.CharField(
        validators=[sap_regex],
        max_length=12,
        blank=False,
        null=False,
        default=None,
        unique=True,
    )
    objects = TeacherManager()

    USERNAME_FIELD = "email"
    REQUIRED_FIELDS = ["sap_id", "department"]

    def __str__(self):
        return self.user.email


class Librarian(User):

    user = models.OneToOneField(User, on_delete=models.CASCADE, parent_link=True)
    user.is_student = False
    user.is_teacher = False
    user.is_librarian = True
    joined_on = models.DateField()
    librarian_id = models.CharField(max_length=12, blank=False, primary_key=True)
    objects = LibrarianManager()

    USERNAME_FIELD = "email"
    REQUIRED_FIELDS = ["librarian_id"]

    def __str__(self):
        return self.user.email


class Book(models.Model):
    isbn = models.CharField(max_length=13, unique=True)
    name = models.CharField(max_length=30)
    publisher = models.CharField(max_length=30)
    author = models.CharField(max_length=30)
    publish_year = models.CharField(max_length=4)
    subject=models.CharField(max_length=30,null=True)

    def __str__(self):
        return self.name + "_" + self.author + "_" + str(self.id)

    @property
    def available_quantity(self):
        return self.copies.filter(is_available=True).count()


    @property
    def demand(self):
        copies = self.copies.objects.filter(book=self)
        return self.copies.issued.objects.filter(copy__in=copies).count()


class Copy(models.Model):
    barcode = models.CharField(max_length=30, unique=True)
    book = models.ForeignKey(Book, on_delete=models.CASCADE)

    CONDITION_CHOICES = (
        ("BEST", "BEST"),
        ("GOOD", "GOOD"),
        ("WORST", "WORST"),
    )
    condition = models.CharField(choices=CONDITION_CHOICES, max_length=30)
    is_available=models.BooleanField(default=True)

    class Meta:
        verbose_name_plural = "Copies"



class WaitingList(models.Model):

    book = models.ForeignKey(Book, on_delete=models.CASCADE)
    teacher = models.ForeignKey(
        Teacher, on_delete=models.CASCADE, blank=True, null=True
    )
    student = models.ForeignKey(
        Student, on_delete=models.CASCADE, blank=True, null=True
    )
    is_alerted = models.BooleanField(default=False)
    alerted_on = models.DateTimeField(blank=True, null=True)

    def __str__(self):
        return self.book.name + "_" + str(self.id)

    class Meta:
        verbose_name_plural = "Waiting List"


class Issue(models.Model):
    copy = models.ForeignKey(Copy, related_name="issued", on_delete=models.CASCADE)
    student = models.ForeignKey(
        Student, on_delete=models.CASCADE, blank=True, null=True
    )
    teacher = models.ForeignKey(
        Teacher, on_delete=models.CASCADE, blank=True, null=True
    )

    issue_date = models.DateField()
    return_date = models.DateField()
    fine = models.IntegerField(default=0)
    paid = models.BooleanField(default=False)

    def __str__(self):
        return self.copy.book.name + "_" + str(self.id)

    class Meta:
        verbose_name_plural = "Issued Books"


class Notification(models.Model):
    issue = models.ForeignKey(Issue, on_delete=models.CASCADE)
    waiting = models.ForeignKey(WaitingList, on_delete=models.CASCADE)
    notification = models.CharField(max_length=50)
