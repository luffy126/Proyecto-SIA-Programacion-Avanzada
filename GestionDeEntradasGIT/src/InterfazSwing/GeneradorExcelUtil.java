package InterfazSwing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Tenerex
 */
public class GeneradorExcelUtil {

    private static final String RUTA_BASE = "GestionDeEntradasGIT/Assets/";
    private static final String CSV_CLIENTES = RUTA_BASE + "clientesRegistrados.csv";
    private static final String CSV_EVENTOS = RUTA_BASE + "eventosRegistrados.csv";
    private static final String RUTA_SALIDA_EXCEL = "ReporteDeSistema.xlsx";
    // El Excel se creará en la raíz del proyecto

    /**
     * Función principal para generar el archivo Excel con los datos de ambos
     * CSV.
     */
    public static void generarExcelDesdeCSVs() {
        // Usa XSSFWorkbook para formato .xlsx
        XSSFWorkbook workbook = new XSSFWorkbook();

        try {
            // 1. Procesar clientesRegistrados.csv
            escribirCSVEnHoja(workbook, CSV_CLIENTES, "Clientes Registrados");

            // 2. Procesar eventosRegistrados.csv
            escribirCSVEnHoja(workbook, CSV_EVENTOS, "Eventos Registrados");

            // 3. Escribir el archivo Excel final
            try (FileOutputStream fileOut = new FileOutputStream(RUTA_SALIDA_EXCEL)) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(null,
                        "✅ Reporte Excel generado exitosamente en: " + new File(RUTA_SALIDA_EXCEL).getAbsolutePath(),
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "❌ Error al generar el archivo Excel: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Lee un archivo CSV y escribe su contenido en una hoja de Excel
     * específica.
     */
    private static void escribirCSVEnHoja(XSSFWorkbook workbook, String csvFilePath, String sheetName) throws IOException {
        File csvFile = new File(csvFilePath);

        if (!csvFile.exists()) {
            throw new IOException("Archivo CSV no encontrado: " + csvFilePath);
        }

        // Crear una nueva hoja para este CSV
        Sheet sheet = workbook.createSheet(sheetName);
        int rowNum = 0;

        // Leer el archivo CSV, asumiendo codificación UTF-8 y delimitador coma (,)
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "UTF-8"))) {
            String line;
            // Iterar sobre cada línea del CSV
            while ((line = br.readLine()) != null) {
                // Asume que el delimitador es una coma (',')
                String[] data = line.split(",");

                Row row = sheet.createRow(rowNum++);
                int cellNum = 0;

                // Iterar sobre cada dato de la línea y crear una celda de Excel
                for (String field : data) {
                    Cell cell = row.createCell(cellNum++);
                    // Intenta determinar si el dato es numérico o texto
                    try {
                        // Verifica si es un número (puede ser entero o decimal)
                        if (field.matches("-?\\d+(\\.\\d+)?")) {
                            cell.setCellValue(Double.parseDouble(field));
                        } else {
                            cell.setCellValue(field.trim());
                        }
                    } catch (NumberFormatException e) {
                        cell.setCellValue(field.trim());
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException("Error de lectura o escritura al procesar " + csvFilePath + ": " + e.getMessage());
        }
    }
}
