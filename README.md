1. Создать gmail
2. Создать новый проект: https://console.cloud.google.com/projectcreate?previousPage=%2Fapis%2Fdashboard%3Fauthuser%3D0%26project%3Dtest-315721&folder=&organizationId=0&authuser=0
3. Включить 'Google Sheet API': https://console.cloud.google.com/apis/library/sheets.googleapis.com?project=perfluencetoolid&authuser=0&folder=
4. Создать OAuth 'Descktop' креды: https://console.cloud.google.com/apis/credentials?authuser=0&folder=&organizationId=&project=perfluencetoolid
5. Добавить юзера из шага 1 как Test user: https://console.cloud.google.com/apis/credentials/consent?authuser=0&project=perfluencetoolid
6. Скачать и подсунить json в 'src/main/resources/google-sheets-client-secret.json'
7. Залогинится через Форму из кода
8. Получить refreshToken
9. Заполнить поля согласно json + refreshToken:
   String CLIENT_ID = "123927302490-123behq5dl5fdf7jg6oc1nv47u1lm123.apps.googleusercontent.com";
   String CLIENT_SECRET = "123JvG9GRQB9sQTkaucG123";
   JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
   HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
   String REFRESH_TOKEN = "1230ckEGmubGVCXICgYIARAAGAwSNwF-L9Irj_123uFNkmNa163HrsY9mzjt9c-pfRgAVD18WQej5FDm8L5YPsPEE-usyWwimffNJTY";
10. Для Необходимого Sheet документа выдать права Редактора для юзера из шага 1
11. Использовать Sheet id необходимого файла 
12. Необходимо опубликовать google application иначе refresh token живет не более 7 дней
    https://console.cloud.google.com/apis/credentials/consent?authuser=0&project=perfluencetoolid&supportedpurview=project

P.S.:
Оргинал кода: https://github.com/eugenp/tutorials/tree/master/libraries-data-io
Без авторизации: https://ru.stackoverflow.com/questions/509701/%D0%90%D0%B2%D1%82%D0%BE%D1%80%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F-%D0%B2-google-%D1%81-%D0%BF%D0%BE%D0%BC%D0%BE%D1%89%D1%8C%D1%8E-oauth-2-%D0%B1%D0%B5%D0%B7-%D0%B8%D1%81%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F-%D0%B1%D1%80%D0%B0%D1%83%D0%B7%D0%B5%D1%80%D0%B0
