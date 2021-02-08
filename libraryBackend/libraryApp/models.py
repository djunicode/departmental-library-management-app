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
