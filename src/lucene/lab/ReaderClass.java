/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucene.lab;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author Rodrigo
 */
public class ReaderClass {
    
    //PerFieldAnalyzerWrapper analyzerWrapper;
    //IndexReader reader;
    //IndexSearcher searcher;
    
    
    public void ReaderClass() throws IOException{
        
        
        //Path indexPath = Paths.get("C:\\index\\");
        //Directory dir = FSDirectory.open(indexPath);
        
        //reader = DirectoryReader.open(dir);
        //searcher = new IndexSearcher(reader);
        
        //analyzerWrapper= this.createAnalyzer();
        
        

    }
    
    
    public void search(String consulta, String field) throws ParseException, IOException{
    
        Path indexPath = Paths.get("C:\\index\\");
        Directory dir = FSDirectory.open(indexPath);
        
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        
        PerFieldAnalyzerWrapper analyzerWrapper= this.createAnalyzer();
         
        Analyzer analyzer = new WhitespaceAnalyzer();
        String[] fields = {"title","artist","text","summary","label"};
        //QueryParser parser = new QueryParser(field, analyzerWrapper);
        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields , analyzer);

        Query query = parser.parse(consulta);
        int hitsPerPage = 100;
        //IndexReader reader = IndexReader.open(index);
        //IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        searcher.search(query, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        
        System.out.println("Encontré " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            
            System.out.println((i + 1) + ". Título: " + d.get("title") +"\t" + "Artista: " + d.get("artist"));
            //System.out.println("Artista: " + d.get("artist"));
                  
        }

        
        
    
    }
    
    
    
    
    public PerFieldAnalyzerWrapper createAnalyzer(){
    
    
        Map<String, Analyzer> analyzerPerField = new HashMap<>();
        
        analyzerPerField.put("title", new WhitespaceAnalyzer() ); 
        analyzerPerField.put("productId", new KeywordAnalyzer()); 
        analyzerPerField.put("userID", new KeywordAnalyzer());
        analyzerPerField.put("profileName", new WhitespaceAnalyzer() );
        analyzerPerField.put("score", new KeywordAnalyzer());
        analyzerPerField.put("summary", new StandardAnalyzer());
        analyzerPerField.put("text", new StandardAnalyzer());
        analyzerPerField.put("artist", new WhitespaceAnalyzer());
        analyzerPerField.put("date",new KeywordAnalyzer());
        analyzerPerField.put("country",new KeywordAnalyzer());
        analyzerPerField.put("barcode", new KeywordAnalyzer());
        analyzerPerField.put("trackCount", new KeywordAnalyzer());
        analyzerPerField.put("label", new WhitespaceAnalyzer());
        analyzerPerField.put("language", new KeywordAnalyzer());
        
        PerFieldAnalyzerWrapper analyzerW = new PerFieldAnalyzerWrapper(new StandardAnalyzer(), analyzerPerField);
        
        return analyzerW;
    }


}







