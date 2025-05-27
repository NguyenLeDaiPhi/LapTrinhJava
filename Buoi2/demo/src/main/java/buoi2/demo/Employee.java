package buoi2.demo;

public class Employee {
    String EmployeeeCode;
    // Default: private
    String fullname;
    // getter, setter
    public String getEmployeeCode() {
        return this.EmployeeeCode;
    }

    public void setEmployee(String _code) {
        this.EmployeeeCode = _code;
    }

    public String fullname() {
        return this.fullname;
    }

    public void setFullname(String _fullname) {
        this.fullname = _fullname;
    }

    public void SaveEmployee (Employee employee) {
        // Logic code deal with managing stored data -> sql
    }

    public void EmployeeReport() {
        // Business logic
        // Export the information employee -> report
        // -> SalaryAvg, SoNamLamViec
    }
}
