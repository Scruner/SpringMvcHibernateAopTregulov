<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<body>
<h2>Employee Info</h2>
<br>

<%--используем Спринг формы--%>
<%--когда мы нажимаем на кнопку "OK" будет срабатывать action--%>
<form:form action="saveEmployee" modelAttribute="employee">
<%--Эта view не знает информации про id работника, поэтому для корректной работы,
мы должны добавить сюда эту информацию. Для этого создаём скрытую форму для Id.
Эта форма не отображается, мы её не увидим, но зато когда эта view вызывается с помощью
метода updateEmployee, поле Id будет не пустым, потому что мы передаём в эту вью
работника из БД, у него поле Id не пустое и оно будет запрлнено, как и все остальные
просто мы не увидим это поле. Но зато после изменений в каком-нибудь поле и нажатии на
кнопку ОК, в метод saveEmployee попадёт работник с конкретным Id--%>
<form:hidden path="id"/>

<%--текстовые формы--%>
    Name<form:input path="name"/>
    <br><br>
    Surname<form:input path="surname"/>
    <br><br>
    Department<form:input path="department"/>
    <br><br>
    Salary<form:input path="salary"/>
    <br><br>
<%--прописываем кнопку "OK", тип кнопки submit, название кнопки "OK"--%>
    <input type="submit" value="OK">
    <%--после того, как мы заполнили поля формы (данные для нового работника на странице браузера,
   нажимаем на кнопку ОК, вызывается метод контроллера с @RequestMapping("/saveEmployee"), его мы и
    прописываем в action--%>
</form:form>

</body>

</html>