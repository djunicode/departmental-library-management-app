from rest_framework import permissions
from .models import *
from rest_framework import status
from rest_framework.exceptions import APIException


class StatusException(APIException):

    status_code = status.HTTP_400_BAD_REQUEST

    def __init__(self, detail, status_code=None):
        self.detail = detail
        if status_code is not None:
            self.status_code = status_code


class IsTeacherStudent(permissions.BasePermission):
    def has_permission(self, request, view):

        if request.user.is_student or request.user.is_teacher:
            return True
        else:
            raise StatusException(
                detail="You are not allowed to access", status_code=400
            )
            return False


class IsLibrarian(permissions.BasePermission):
    def has_permission(self, request, view):

        if request.user.is_librarian:
            return True
        else:
            raise StatusException(
                detail="You are not allowed to access", status_code=400
            )
            return False
