package electro_wallet.exception;

public interface ErrorMessages {

    // Ошибки пользователей (400, 404)
    String USER_NOT_FOUND = "Пользователь с таким ID не найден.";
    String PHONE_NUMBER_NOT_FOUND = "Номер телефона не найден";
    String EMAIL_ALREADY_EXISTS = "Пользователь с таким email уже зарегистрирован.";
    String PHONE_ALREADY_EXISTS = "Номер телефона уже используется другим пользователем.";

    // Ошибки аккаунтов и кошелька (400, 404)
    String ACCOUNT_NOT_FOUND = "Счет пользователя не найден.";
    String INSUFFICIENT_FUNDS = "Недостаточно средств на балансе для совершения операции.";
    String SAME_ACCOUNT_TRANSFER = "Перевод самому себе невозможен.";
    String INVALID_AMOUNT = "Сумма перевода должна быть больше нуля.";

    // Общие системные ошибки (500)
    String INTERNAL_SERVER_ERROR = "Произошла внутренняя ошибка сервера. Попробуйте позже.";
    String DATA_INTEGRITY_ERROR = "Ошибка сохранения данных в базу.";

    // Ошибки безопасности (401, 403)
    String UNAUTHORIZED = "Ошибка аутентификации. Пожалуйста, войдите в систему.";
    String ACCESS_DENIED = "У вас недостаточно прав для выполнения этой операции.";
    String LOGIN_FAILED = "Неверный email или пароль.";

}
