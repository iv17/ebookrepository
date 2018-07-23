package rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.analysers;

import java.io.Reader;
import java.text.Normalizer;
import java.text.Normalizer.Form;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.filters.CyrilicToLatinFilter;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.filters.CyrillicLatinConverter;

public class SerbianAnalyzer extends Analyzer {

    /**
     * An array containing some common English words
     * that are usually not useful for searching.
     */
    public static final String[] STOP_WORDS =
    {
        "i","a","ili","ali","pa","te","da","u","po","na",
        "biti", "ne", 
		"jesam", "sam", "jesi", "si", "je", "jesmo", "smo", "jeste", "ste", "jesu", "su",
		"nijesam", "nisam", "nijesi", "nisi", "nije", "nijesmo", "nismo", "nijeste", "niste", "nijesu", "nisu",
		"budem", "budeš", "bude", "budemo", "budete", "budu",
		"budes",
		"bih",  "bi", "bismo", "biste", "biše", 
		"bise",
		"bio", "bili", "budimo", "budite", "bila", "bilo", "bile", 
		"ću", "ćeš", "će", "ćemo", "ćete", 
		"neću", "nećeš", "neće", "nećemo", "nećete", 
		"cu", "ces", "ce", "cemo", "cete",
		"necu", "neces", "nece", "necemo", "necete",
		"mogu", "možeš", "može", "možemo", "možete",
		"mozes", "moze", "mozemo", "mozete",
		"li", "da"
    };

    /**
     * Builds an analyzer.
     */
    public SerbianAnalyzer()
    {
    }

    public static String analize(String text) {
    	String newText = text.toLowerCase();
    	newText = Normalizer.normalize(CyrillicLatinConverter.cir2lat(newText), Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		return newText;
    }
    
	@Override
	protected TokenStreamComponents createComponents(String arg0) {
		Tokenizer source = new StandardTokenizer();
		TokenStream result = new CyrilicToLatinFilter(source);
	    result = new LowerCaseFilter(result);
	    result = new StopFilter(result,StopFilter.makeStopSet(STOP_WORDS));
		return new TokenStreamComponents(source, result){
		      @Override
		      protected void setReader(final Reader reader) {
		        super.setReader(reader);
		      }
		    };
	}

}
