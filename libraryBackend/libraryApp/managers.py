from django.contrib.auth.base_user import BaseUserManager

class UserManager(BaseUserManager):
    use_in_migrations = True

    def create_user(self, email, first_name, last_name,address,mobile, password=None, **extra_fields):
        if not email:
            raise ValueError('Users must have an email address')
        user = self.model(first_name=first_name, last_name=last_name,address=address,mobile=mobile, email=self.normalize_email(email),)
        user.set_password(password)
        user.save(using = self._db)
        return user
    def create_superuser(self, email, first_name,address,mobile, last_name, password, **extra_fields):
        extra_fields.setdefault('is_superuser', True)
        user = self.create_user(first_name=first_name, last_name=last_name,address=address,mobile=mobile, email=self.normalize_email(email), password=password)
        user.is_admin = True
        user.is_staff = True
        user.is_superuser = True
        user.save(using = self._db)
        return user


class StudentManager(BaseUserManager):
    use_in_migrations = True

    def create_user(self, email, is_student, is_teacher,department,sap_id,graduation_year, first_name, last_name, password=None, **extra_fields):
        if not email:
            raise ValueError('Users must have an email address')
        user = self.model(first_name=first_name, last_name=last_name,department=department,sap_id=sap_id,graduation_year=graduation_year, email=self.normalize_email(email),is_student = True, is_teacher = False)
        user.set_password(password)
        user.save(using = self._db)
        return user