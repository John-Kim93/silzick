from accounts.serializers import UserSerializer
from rest_framework import serializers
from .models import Notice, Request
from django.contrib.auth import get_user_model

class NoticeSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Notice
        fields = ('id', 'title', 'content', 'created_at', 'updated_at',)


class RequestSerializer(serializers.ModelSerializer):
    class UserSerializer(serializers.ModelSerializer):
        class Meta:
            model = get_user_model()
            fields = ('username','password', )

    class Meta:
        model = Request
        fields = ('title', 'content',)