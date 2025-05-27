package buoi2.demo;

import java.util.List;
public class EmployeeReport {
    Double SalaryAvg;
    Double SoNamLamViec;
    String Type;
    public void ExportReport(List<Employee> employee) {
        // Report /...
        if (Type == "Excel") {
            // Logic Export Excel
        }

        if (Type == "PDF") {
            // Logic Export PDF
        }
    }
}
