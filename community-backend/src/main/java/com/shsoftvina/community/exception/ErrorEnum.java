package com.shsoftvina.community.exception;

import lombok.Getter;

@Getter
public enum ErrorEnum {

	ID_OAUTH2_NOT_FOUND("Entity", "IdOAuth2NotFound", "Id oauth2 not found"),
	NO_PERMISSION("Entity", "NotPermission", "Not permission"),
	NOT_SUPPORT("Entity", "NotSupport", "Not support"),
	DUPLICATE_DATA("Entity", "DuplicateData", "Duplicate data"),
	ENUM_CONVERT_ERROR("Entity", "EnumConvertError", "Error convert enum"),
	JSON_CONVERT_TO_OBJECT_ERROR("Entity", "JsonConvertError", "Error convert json to object"),
	DATA_TIME_ERROR("Entity", "DataTimetError", "Data time error"),
	NOT_FOUND("Entity", "NotFound", "Entity is not found"),
	ID_NOT_FOUND("Entity", "NotFound", "Id is not found"),
	MEDIA_TYPE_NOT_SUPPORT("Media", "NotSupported", "Media type is not supported"),
	USER_NOT_FOUND("User", "NotFound", "User is not found"),
	USER_NAME_ALREADY_EXISTED("User", "UserNameAlreadyExisted", "Username is already existed"),
	EMAIL_ALREADY_EXISTED("User", "UserEmailAlreadyExisted", "Email is already existed"),
	POST_NOT_FOUND("Post", "PostNotFound", "Post is not found"),
	POST_DUPLICATE_USER_ACCESS_DETAIL("Post", "DuplicateUserAccessPostDetail", "Duplicate user access post detail"),
	COMMENT_NOT_FOUND("Post", "CommentNotFound", "Comment is not found"),
	COMPONENT_ALREADY_EXISTED("Component", "ComponentAlreadyExisted", "Component already existed"),
	COMPONENT_CATEGORY_NOT_FOUND("Component category", "ComponentCategoryNotFound", "Component category is not found"),
	COMPONENT_CATEGORY_ALREADY_EXISTED("Component category", "ComponentCategoryAlreadyExisted", "Component category already existed"),
	COMPONENT_NOT_FOUND("Component", "ComponentNotFound", "Component is not found"),
	EXAMPLE_NOT_FOUND("Example", "ExampleNotFound", "Example is not found"),
	BOOK_NOT_FOUND("Book", "BookNotFound", "Book is not found"),
	PASSWORD_NOT_MATCH("User", "UserPasswordNotMatch", "User password not match"),
	GROUP_NOTI_NOT_FOUND("GroupNoti", "GroupNotiNotFound", "Group noti not found"),
	NOTIFICATION_NOT_FOUND("Notification", "NotificationNotFound", "Notification not found");

	private final String entityName;
	private final String errorKey;
	private final String message;
	
	ErrorEnum(String entityName, String errorKey, String message) {
		this.entityName = entityName;
		this.errorKey = errorKey;
		this.message = message;
	}
}
