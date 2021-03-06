package pl.polsl.reservations.client.views.utils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.polsl.reservations.client.reports.PDFDocument;

/**
 *
 * @author matis
 */
public class FontUtils {

    protected static BaseFont timesRomanBase;

    static {
        try {
            timesRomanBase = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(PDFDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static final Font TITLE_BOLD_UNDERLINED
            = new Font(timesRomanBase, 20, Font.BOLD | Font.UNDERLINE);
    public static final Font BOLD
            = new Font(timesRomanBase, 12, Font.BOLD);
    public static final Font NORMAL
            = new Font(timesRomanBase, 12);
    public static final Font SMALL
            = new Font(timesRomanBase, 5);
    public static final Font SMALL_BOLD
            = new Font(timesRomanBase, 5, Font.BOLD);
}
