from rest_framework.pagination import PageNumberPagination

class BookListPagination(PageNumberPagination):
    page_size = 5
    page_size_query_param = 'records'
    max_page_size = 5
