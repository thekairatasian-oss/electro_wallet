package electro_wallet.exception;

public interface ErrorMessages {

    // --- Общие ошибки (400, 500) ---
    String INTERNAL_SERVER_ERROR = "Произошла внутренняя ошибка сервера";
    String INVALID_REQUEST_PARAMETERS = "Переданы некорректные параметры запроса";
    String VALIDATION_FAILED = "Ошибка валидации входных данных";

    // --- Ресурс не найден (404) ---
    String USER_NOT_FOUND = "Пользователь с данным идентификатором не найден";
    String ACCOUNT_NOT_FOUND = "Счет не найден";
    String TRANSACTION_NOT_FOUND = "Транзакция не найдена";

    // --- Безопасность и доступ (401, 403) ---
    String ACCESS_DENIED = "У вас недостаточно прав для выполнения этой операции";
    String UNAUTHORIZED = "Пользователь не авторизован";

    // --- Конфликты и бизнес-логика (409, 422) ---
    String EMAIL_ALREADY_EXISTS = "Пользователь с таким email уже существует";
    String PHONE_NUMBER_ALREADY_EXISTS = "Пользователь с таким номером телефона уже существует";
    String INSUFFICIENT_FUNDS = "На счету недостаточно средств для совершения транзакции";
    String SAME_ACCOUNT_TRANSFER = "Перевод на тот же счет невозможен";
    String CANNOT_TRANSFER_TO_SELF = "Перевод самому себе невозможен";

    // --- Ошибки для админа ---
    String USER_ALREADY_BLOCKED = "Пользователь уже заблокирован";
    String CANNOT_DELETE_SELF = "Администратор не может удалить самого себя";
}
