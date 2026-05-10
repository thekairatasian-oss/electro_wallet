package electro_wallet.exception;

public interface ErrorMessages {

    // --- 400 BAD REQUEST (Ошибки валидации и логики) ---
    interface BadRequest {
        String INVALID_PHONE_FORMAT = "Номер телефона должен начинаться с 996 и содержать 12 цифр";
        String DUPLICATE_EMAIL = "Пользователь с таким Email уже существует";
        String DUPLICATE_PHONE = "Этот номер телефона уже привязан к другому аккаунту";
        String INSUFFICIENT_FUNDS = "Недостаточно средств на балансе для совершения перевода";
        String SAME_ACCOUNT_TRANSFER = "Перевод самому себе невозможен";
        String INVALID_AMOUNT = "Сумма перевода должна быть больше нуля";
        String EMAIL_ALREADY_EXISTS = "Пользователь с таким Email уже зарегистрирован";
        String PHONE_ALREADY_EXISTS = "Пользователь с таким номером телефона уже зарегистрирован";
    }

    // --- 404 NOT FOUND (Ресурсы не найдены) ---
    interface NotFound {
        String USER_NOT_FOUND = "Пользователь не найден";
        String PHONE_NUMBER_NOT_FOUND = "Номер телефона не найден";
        String TRANSACTION_NOT_FOUND = "История транзакций пуста или запись не найдена";
    }

    // --- 403 FORBIDDEN (Безопасность и роли) ---
    interface Forbidden {
        String ACCESS_DENIED = "У вас нет прав для выполнения этой операции";
        String ACCOUNT_BLOCKED = "Ваш аккаунт заблокирован администратором";
    }

    // --- 401 UNAUTHORIZED (Проблемы с аутентификацией) ---
    interface Unauthorized {
        String BAD_CREDENTIALS = "Неверное имя пользователя или пароль";
    }

    // --- 500 INTERNAL SERVER ERROR (Непредвиденные ошибки) ---
    interface ServerError {
        String UNEXPECTED_ERROR = "Произошла непредвиденная ошибка на сервере. Попробуйте позже";
    }
}
