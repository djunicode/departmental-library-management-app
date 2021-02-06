"""Unicode_REST_API URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.urls import path, include

from .api import *
from rest_framework.authtoken.views import obtain_auth_token  # <-- Here

urlpatterns = [
    # WORKING STUFF:
    path("user-register/", RegisterUser.as_view(), name="user-register"),
    path("student-register/", RegisterStudent.as_view(), name="student-register"),
    path("teacher-register/", RegisterTeacher.as_view(), name="teacher-register"),
    path("librarian-register/", RegisterLibrarian.as_view(), name="librarian-register"),
    # path("login/", login, name="login"),
    path("login/", obtain_auth_token, name="login"),  # <-- And here
]
