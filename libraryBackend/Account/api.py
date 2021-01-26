from rest_framework import generics, permissions
from rest_framework.response import Response
from rest_framework.permissions import AllowAny
#from knox.models import AuthToken
from .models import *
from rest_framework.views import APIView
from rest_framework.generics import CreateAPIView
from .serializers import UserSerializer,UserRegisterSerializer ,RegisterSerializer, LoginSerializer, StudentSerializer, StudentDisplaySerializer, TeacherDisplaySerializer
from .serializers import RegisterSerializer1,StudentRegisterSerializer1, TeacherRegisterSerializer1, LibrarianRegisterSerializer1
from django.contrib.auth import login
from django.shortcuts import get_object_or_404
from rest_framework import permissions
from django.contrib.auth import authenticate
from django.views.decorators.csrf import csrf_exempt
from rest_framework.authtoken.models import Token
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from rest_framework.status import (
    HTTP_400_BAD_REQUEST,
    HTTP_404_NOT_FOUND,
    HTTP_200_OK
)

#from knox.views import LoginView as KnoxLoginView

class CreateUserView(CreateAPIView):
    model = User
    permission_classes = [
        permissions.AllowAny # Or anon users can't register
    ]
    serializer_class = UserRegisterSerializer




class UserDisplayView(APIView):
    def get(self, request, pk=None):
        if pk:
            user = get_object_or_404(User.objects.all(), pk=pk)
            serializer = UserSerializer(user)
            return Response({"user": serializer.data})
        users = User.objects.all()
        serializer = TeacherDisplaySerializer(users, many=True)
        return Response({"users": serializer.data})


class StudentDisplayView(APIView):
    def get(self, request, pk=None):
        if pk:
            student = get_object_or_404(Student.objects.all(), pk=pk)
            serializer = StudentDisplaySerializer(student)
            return Response({"student": serializer.data})
        students = Student.objects.all()
        serializer = StudentDisplaySerializer(students, many=True)
        return Response({"students": serializer.data})


class TeacherDisplayView(APIView):
    def get(self, request, pk=None):
        if pk:
            teacher = get_object_or_404(Teacher.objects.all(), pk=pk)
            serializer = TeacherDisplaySerializer(teacher)
            return Response({"teacher": serializer.data})
        teachers = Teacher.objects.all()
        serializer = UserSerializer(teachers, many=True)
        return Response({"teachers": serializer.data})




class UserAPIView(generics.RetrieveAPIView):
    permission_classes = [
        permissions.IsAuthenticated,
    ]
    serializer_class = UserSerializer

    def get_object(self):
        return self.request.user

    def get(self, request):
        return self.request.user


class RegisterAPIView(generics.CreateAPIView):

    serializer_class = RegisterSerializer
    permission_classes = (AllowAny, )
    queryset = User.objects.all()

 #def post(self, request, *args, **kwargs):
 #       serializer = self.get_serializer(data=request.data)
  #      serializer.is_valid(raise_exception=True)
   #     user = serializer.save()
   #     return Response({
   #         "user": UserSerializer(user, context=self.get_serializer_context()).data,
   #         "token": AuthToken.objects.create(user)[1]
   #     })




class LoginAPIView(generics.GenericAPIView):
    serializer_class = LoginSerializer

    def post(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        user = serializer.validated_data

        return Response({
            'user': UserSerializer(user).data,
            'token': AuthToken.objects.create(user)[1]
        })
"""         NEW STUFF           """





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
        return Response({
        "user": UserSerializer(user, context=self.get_serializer_context()).data,
        "token": AuthToken.objects.create(user)[1]
        })

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
        return Response({'token': token.key},status=HTTP_200_OK)
        #return Response({
        #"user": StudentDisplaySerializer(user, context=self.get_serializer_context()).data,
        #"token": AuthToken.objects.create(user)[1]
        #})


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
        return Response({'token': token.key},status=HTTP_200_OK)

        #return Response({
        #"user": TeacherDisplaySerializer(user, context=self.get_serializer_context()).data,
        #"token": AuthToken.objects.create(user)[1]
        #})


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
        return Response({'token': token.key},status=HTTP_200_OK)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def login(request):
    username = request.data.get("username")
    password = request.data.get("password")
    if username is None or password is None:
        return Response({'error': 'Please provide both username and password'},
                        status=HTTP_400_BAD_REQUEST)
    user = authenticate(username=username, password=password)
    if not user:
        return Response({'error': 'Invalid Credentials'},
                        status=HTTP_404_NOT_FOUND)
    token, _ = Token.objects.get_or_create(user=user)
    return Response({'token': token.key},
                    status=HTTP_200_OK)