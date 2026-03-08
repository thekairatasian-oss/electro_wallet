package electro_wallet.exception;

public interface ErrorMessages {


        // 400 Bad Request (Ошибки клиента, неправильные данные)
        String VALIDATION_FAILED = "Ошибка валидации данных";
        String INVALID_PHONE_FORMAT = "Некорректный формат номера телефона. Ожидается +996XXXXXXXXX";
        String SELF_TRANSFER_NOT_ALLOWED = "Нельзя переводить средства самому себе";
        String INSUFFICIENT_FUNDS = "Недостаточно средств на счете";
        String INVALID_CURRENCY = "Данная валюта не поддерживается";

        // 403 Forbidden (Нет прав доступа)
        String ACCESS_DENIED = "У вас нет прав для выполнения этой операции";
        String BLOCKED_USER = "Ваш аккаунт заблокирован";

        //  404 Not Found (Ресурс не найден)
        String USER_NOT_FOUND = "Пользователь не найден";
        String ACCOUNT_NOT_FOUND = "Аккаунт не найден";
        String RECEIVER_NOT_FOUND = "Получатель с таким номером телефона не существует";
        String TRANSFER_NOT_FOUND = "Перевод не найден";

        // 409 Conflict (Конфликт данных)
        String USER_ALREADY_EXISTS = "Пользователь с таким email уже существует";
        String PHONE_NUMBER_ALREADY_EXISTS = "Номер телефона уже зарегистрирован";

        // 500 Internal Server Error (Ошибки сервера)
        String INTERNAL_SERVER_ERROR = "Внутренняя ошибка сервера, попробуйте позже";
        String BALANCE_ERROR = "Ошибка при обработке баланса";


}
