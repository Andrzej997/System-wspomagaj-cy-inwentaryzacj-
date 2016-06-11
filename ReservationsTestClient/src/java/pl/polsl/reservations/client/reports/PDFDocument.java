package pl.polsl.reservations.client.reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *
 * @author matis
 */
public abstract class PDFDocument {

    protected String pathToResultFile;
    protected final Document document;
    protected Rectangle pageSize;
    protected static final String ENCODING = "UTF-8";

    public PDFDocument() {
        pathToResultFile = null;
        document = new Document(PageSize.A4);
    }

    public PDFDocument(String pathToResultFile, Rectangle pageSize) {
        this.pathToResultFile = pathToResultFile;
        document = new Document(pageSize);
    }

    public void generatePDF() throws DocumentException, FileNotFoundException {
        PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(pathToResultFile));
        instance.setPdfVersion(PdfWriter.VERSION_1_7);
        document.open();
        generateHeader();
        generateContent();
        document.close();

    }

    public abstract void generateHeader();

    public abstract void generateContent();

    /**
     * @return the pathToResultFile
     */
    public String getPathToResultFile() {
        return pathToResultFile;
    }

    /**
     * @param pathToResultFile the pathToResultFile to set
     */
    public void setPathToResultFile(String pathToResultFile) {
        this.pathToResultFile = pathToResultFile;
    }


}
