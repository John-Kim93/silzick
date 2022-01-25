from rest_framework import status
from rest_framework.response import Response
from rest_framework.decorators import api_view

from django.shortcuts import get_object_or_404

from .serializers import RequestSerializer, NoticeSerializer
from .models import Request, Notice


@api_view(['GET', 'POST'])
def request_create(request):
    if request.method == 'GET':
        requests = Request.objects.all()
        serializer = RequestSerializer(requests, many=True)
        return Response(serializer.data)

    elif request.method == 'POST':
        serializer = RequestSerializer(data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)

@api_view(['PUT', 'DELETE'])
def request_update_delete(request, request_pk):
    requests = get_object_or_404(Request, pk=request_pk)

    if request.method == 'PUT':
        serializer = RequestSerializer(requests, data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
            return Response(serializer.data)

    elif request.method == 'DELETE':
        requests.delete()
        return Response({ 'id': request_pk }, status=status.HTTP_204_NO_CONTENT)

@api_view(['GET', 'POST'])
def notice_create(request):
    if request.method == 'GET':
        notices = Notice.objects.all()
        serializer = NoticeSerializer(notices, many=True)
        return Response(serializer.data)

    elif request.method == 'POST':
        serializer = NoticeSerializer(data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)


@api_view(['PUT', 'DELETE'])
def notice_update_delete(request, notice_pk):
    notice = get_object_or_404(Notice, pk=notice_pk)

    if request.method == 'PUT':
        serializer = NoticeSerializer(notice, data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
            return Response(serializer.data)

    elif request.method == 'DELETE':
        notice.delete()
        return Response({ 'id': notice_pk }, status=status.HTTP_204_NO_CONTENT)

