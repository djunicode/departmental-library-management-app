from django.contrib.auth.base_user import BaseUserManager


class UserManager(BaseUserManager):
    use_in_migrations = True

    def create_user(
        self, email, password=None, **extra_fields
    ):
        if not email:
            raise ValueError("Users must have an email address")
        user = self.model(
            email=self.normalize_email(email),
        )
        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_superuser(self, email,password, **extra_fields):
        extra_fields.setdefault("is_superuser", True)
        user = self.create_user(
            email=self.normalize_email(email),
            password=password,
        )
        user.is_admin = True
        user.is_staff = True
        user.is_superuser = True
        user.save(using=self._db)
        return user


class StudentManager(BaseUserManager):
    use_in_migrations = True

    def create_user(
        self,
        email,
        is_student,
        is_teacher,
        is_librarian,
        department,
        sap_id,
        first_name,
        last_name,
        password=None,
        **extra_fields
    ):
        if not email:
            raise ValueError("Users must have an email address")
        user = self.model(
            first_name=first_name,
            last_name=last_name,
            department=department,
            sap_id=sap_id,
            email=self.normalize_email(email),
            is_student=True,
            is_teacher=False,
            is_librarian=False,
        )
        user.set_password(password)
        user.save(using=self._db)
        return user


class TeacherManager(BaseUserManager):
    use_in_migrations = True

    def create_user(
        self,
        email,
        is_student,
        is_teacher,
        is_librarian,
        department,
        sap_id,
        first_name,
        last_name,
        password=None,
        **extra_fields
    ):
        if not email:
            raise ValueError("Users must have an email address")
        user = self.model(
            first_name=first_name,
            last_name=last_name,
            department=department,
            sap_id=sap_id,
            email=self.normalize_email(email),
            is_student=False,
            is_teacher=True,
            is_librarian=False,
        )
        user.set_password(password)
        user.save(using=self._db)
        return user


class LibrarianManager(BaseUserManager):
    use_in_migrations = True

    def create_user(
        self,
        email,
        is_student,
        is_teacher,
        is_librarian,
        first_name,
        last_name,
        joined_on,
        librarian_id,

        password=None,
        **extra_fields
    ):
        if not email:
            raise ValueError("Users must have an email address")
        user = self.model(
            first_name=first_name,
            last_name=last_name,
            librarian_id=librarian_id,
            joined_on=joined_on,

            email=self.normalize_email(email),
            is_student=False,
            is_teacher=False,
            is_librarian=True,
        )
        user.set_password(password)
        user.save(using=self._db)
        return user
