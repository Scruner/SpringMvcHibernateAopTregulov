package ru.vdv.tregulov.mvc_hibernate_aop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vdv.tregulov.mvc_hibernate_aop.dao.EmployeeDAO;
import ru.vdv.tregulov.mvc_hibernate_aop.entity.Employee;
import ru.vdv.tregulov.mvc_hibernate_aop.service.EmployeeService;

import java.util.List;

@Controller
public class MyController {

    //чтобы в ручную не создавать объект класса EmployeeDAOImpl, мы используем DI(внедрение зависимости)
    // через интерфейс EmployeeDAO
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/")
    //чтобы view смог отобразить значения полей наших работников, мы должны в методе контроллера
    // создать модель и добавить наших работников в качестве атрибута к этой модели.
    public String showAllEmployees(Model model) {
        //вызываем метод getAllEmployees(), чтобы он вернул нам список работников, его рузультат
        // помещаем в переменную allEmployees типа Employee
        List<Employee> allEmployees = employeeService.getAllEmployees();
        // Помещаем в эту модель атрибут (назовём атрибут "allEmps", значение атрибута allEmployees,
        // т.е. все наши работники). Теперь view будет использовать данную модель, возьмёт из данного
        // атрибута всю необходимую информацию, для отображения в браузере
        model.addAttribute("allEmps", allEmployees);
        return "all-employees";
    }

    @RequestMapping("/addNewEmployee")
    public String addNewEmployee(Model model) {
        //создаём пустого работника, чтобы формы были пустые
        Employee employee = new Employee();
        //помещаем пустого работника в атрибут модели. Мы делаем это потому что, когда мы будем
        // заполнять форму отдельного работника данными, мы будем добавлять данные из формы
        // этому работнику
        model.addAttribute("employee", employee);
        return "employee-info";
    }

    @RequestMapping("/saveEmployee")
    //для сохранения работника в этом методе мы пишем аннотацию @ModelAttribute, атрибут (мы создали
    // его выше) называется "employee". Таким образом получили из формы view наш атрибут employee
    // (а это уже работник с заполненными данными)
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        //вызываем метода saveEmployee, используя employeeService и в параметр этого метода,
        //помещаем этого работника. В свою очередь сервис вызывает метод ДАО (методы называются одинаково,
        // но это разные методы) и передаёт ему работника employee из своего параметра
        employeeService.saveEmployee(employee);

        return "redirect:/";
    }

    @RequestMapping("/updateInfo")
    //мы знаем, что при вызове этого метода, получаем Id работника, который был прописан в ссылке для
    // кнопки, поэтому в параметре метода мы можем использовать данный Id, используя аннотацию
    // @RequestParam(в скобках указываем имя этого параметра) и называется параметр id типа int
    public String updateEmployee(@RequestParam("empId") int id, Model model) {
        //для того, чтобы отобразить этот view "employee-info" с уже заполненными формами, мы
        // должны послать ему модель, которая в качестве атрибута будет содержать работника, поэтому в
        // параметре метода мы прописываем Model
        Employee employee = employeeService.getEmployee(id);
        //добавляем атрибут к этой модели, соответствующее view уже работает с атрибутом employee,
        // поэтому здесь атрибут должен называться также, иначе вью просто не распознает этот атрибут
        model.addAttribute("employee", employee);
        return "employee-info";
    }

    @RequestMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("empId") int id) {
       employeeService.deleteEmployee(id);
        return "redirect:/";
    }
}

