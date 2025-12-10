package com.kdw.wb.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

	CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "コンテンツが見つかりません", "content not found"),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ユーザーが見つかりません", "user not found"),
	ENGINEER_NOT_FOUND(HttpStatus.NOT_FOUND, "エンジニアデータが見つかりません", "engineer not found"),
	SALES_NOT_FOUND(HttpStatus.NOT_FOUND, "営業データが見つかりません", "sales not found"),
	COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "会社データが見つかりません", "company not found"),
	
	AUTHENTICATION_REQUIRED(HttpStatus.UNAUTHORIZED, "ログインしてください", "login please"),
	
	USERNAME_CONFLICT(HttpStatus.CONFLICT, "ユーザー名が重複しています", "username conflict"),
	
	INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "パスワードが正しくありません", "incorrect password"),
	INVALID_JWT(HttpStatus.UNAUTHORIZED, "無効な接続認証トークンです", "invalid jwt"),
	EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "接続認証トークンの有効期限が切れました", "expired jwt"),
	
	INVALID_CONTENT_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "サポートされていないコンテンツタイプです", "unsupported content type"),
	
	PASSKEY_REQUIRED(HttpStatus.BAD_REQUEST, "パスキーが必要です", "passkey is required"),
	FILE_REQUIRED(HttpStatus.BAD_REQUEST, "ファイルが添付されていません", "file is required"),
	INVALID_INTEGER_VALUE(HttpStatus.BAD_REQUEST, "入力された番号を確認してください", "the value is not a integer value"),
	
	UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "システムエラーが発生しました。管理者にお問い合わせください", "unknown server error")
	;
	private final HttpStatus status;
	private final String message;
	private final String detail;
}