from django.urls import path
from . import views


urlpatterns = [
    path('request/', views.request_create),
    path('notice/', views.notice_create),
    path('request/<int:request_pk>/', views.request_update_delete),
    path('notice/<int:notice_pk>/', views.notice_update_delete),
]
