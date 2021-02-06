from rest_framework import generics, permissions
from rest_framework.response import Response
from rest_framework.permissions import AllowAny
from .models import *

from rest_framework.views import APIView
from rest_framework.generics import CreateAPIView
from .serializers import *

from django.contrib.auth import login
from django.shortcuts import get_object_or_404
from rest_framework import permissions
from django.contrib.auth import authenticate
from django.views.decorators.csrf import csrf_exempt
from rest_framework.authtoken.models import Token
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from rest_framework.status import HTTP_400_BAD_REQUEST, HTTP_404_NOT_FOUND, HTTP_200_OK





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
        token, _ = Token.objects.get_or_create(user=user)
        return Response({"token": token.key}, status=HTTP_200_OK)

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
        token, _ = Token.objects.get_or_create(user=user)
        return Response({"token": token.key}, status=HTTP_200_OK)
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
        token, _ = Token.objects.get_or_create(user=user)
        return Response({"token": token.key}, status=HTTP_200_OK)


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
        token, _ = Token.objects.get_or_create(user=user)
        return Response({"token": token.key}, status=HTTP_200_OK)