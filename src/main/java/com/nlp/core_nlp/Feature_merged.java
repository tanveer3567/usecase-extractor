package com.nlp.core_nlp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.nlp.util.ClassificationCoreLabel;
import com.nlp.util.FileHelper;
import com.nlp.wordnet.WordNetService;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;

public class Feature_merged {

	static String line = null;
	static FileHelper fileHelper = new FileHelper();
	static Date date = new Date();
	static Map<String, String> vcat = new HashMap<String, String>();
	static List<FeatureVector> vectors = new ArrayList<FeatureVector>();
	public static final String PROPERTIESLIST = "tokenize,ssplit,pos,lemma,depparse,natlog,openie,ner";

	ClassLoader classLoader = getClass().getClassLoader();
	private File folder = null;

	public Feature_merged(String folderName) throws IOException {

		this.folder = new File(classLoader.getResource(folderName).getFile());
		if (this.folder == null) {
			throw new FileNotFoundException("Folder " + folderName + " does not exist.");
		}
	}

	public static void readFileFromGUI(final String filename){

		ClassificationCoreLabel classificationCoreLabel = null;
		ArrayList<ClassificationCoreLabel> listOfClassificationPerWord = null;
		String[] oieSubjectArray = null;
		String[] oieObjectArray = null;
		String[] oieRelationArray = null;
		BufferedReader fileContent = null;
		System.out.println(filename);
		try {
			fileContent = new BufferedReader(new FileReader(new File(filename)));
			String tempLine;
			while ((tempLine = fileContent.readLine()) != null) {
				line = tempLine;
				Properties properties = PropertiesUtils.asProperties("annotators", PROPERTIESLIST);
				StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
				Annotation annotation = new Annotation(line);
				pipeline.annotate(annotation);
				for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
					listOfClassificationPerWord = new ArrayList<ClassificationCoreLabel>();
					for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
						String word = token.get(CoreAnnotations.TextAnnotation.class);
						String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
						String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
						classificationCoreLabel = new ClassificationCoreLabel(word, pos, ner);
						listOfClassificationPerWord.add(classificationCoreLabel);
					}
					Collection<RelationTriple> triples = sentence
							.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
					RelationTriple triple = getLongestTriple(triples);
					if (triple == null) {
						continue;
					}
					oieSubjectArray = triple.subjectGloss().split(" ");
					oieObjectArray = triple.objectGloss().split(" ");
					oieRelationArray = triple.relationGloss().split(" ");
					String subjectPos = null;
					String objectPos = null;
					String relationPos = null;
					String subjectNer = null;
					String objectNer = null;
					for (ClassificationCoreLabel ccl : listOfClassificationPerWord) {
						if (ccl.getWord().equals(oieSubjectArray[oieSubjectArray.length - 1])) {
							subjectPos = ccl.getPos();
							subjectNer = ccl.getNer();
						}
						if (ccl.getWord().equals(oieObjectArray[oieObjectArray.length - 1])) {
							objectPos = ccl.getPos();
							objectNer = ccl.getNer();
						}
						if (ccl.getWord().equals(oieRelationArray[oieRelationArray.length - 1])) {
							relationPos = ccl.getPos();
						}
					}
					vectors.add(new FeatureVector(triple.subjectGloss(), subjectPos, subjectNer,
							((WordNetService.getInstance().isScalar(triple.subjectGloss())) ? 1 : 0),
							triple.relationGloss(), relationPos, getvcat(triple.relationGloss()), "Y",
							triple.objectGloss(), objectPos, objectNer,
							((WordNetService.getInstance().isScalar(triple.objectGloss())) ? 1 : 0)));
				}
			}
			writeToFeatureVector();

		} catch (Exception e) {
			System.out.println("Program encountered an error while processing the file : " + e);
		} finally {
			if (fileContent != null)
				try {
					fileContent.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private static void writeToFeatureVector() {

		try {
			PrintWriter writer = new PrintWriter(new File("C:\\Users\\Tanveer\\Desktop\\result.txt"));
			vectors.forEach(vector -> {
				System.out.println(vector.toString());
				writer.append(vector.toString());
				writer.flush();
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static RelationTriple getLongestTriple(Collection<RelationTriple> triples) {
		int count = 0;
		int value = 0;
		RelationTriple resultTriple = null;
		Map<RelationTriple, Integer> tripleCountMap = new HashMap<RelationTriple, Integer>();

		for (RelationTriple triple : triples) {
			count += triple.subjectGloss().split(" ").length;
			count += triple.objectGloss().split(" ").length;
			count += triple.relationGloss().split(" ").length;
			tripleCountMap.put(triple, count);
			count = 0;
		}

		for (Map.Entry<RelationTriple, Integer> entry : tripleCountMap.entrySet()) {
			if (value < entry.getValue()) {
				value = entry.getValue();
				resultTriple = entry.getKey();
			}
		}

		return resultTriple;
	}

	public static void addVerbCategories() {
		vcat.put("has", "possession");
		vcat.put("have", "possession");
		vcat.put("had", "possession");
		vcat.put("possess", "possession");
		vcat.put("consist of", "comprised of");
		vcat.put("comprised of", "comprised of");
		vcat.put("constituent of", "comprised of");
		vcat.put("compose", "consist");
		vcat.put("form", "consist");
		vcat.put("composed", "consist");
		vcat.put("formed", "consist");
		vcat.put("consist", "consist");
		vcat.put("encompass", "consist");
		vcat.put("embrace", "consist");
		vcat.put("constituted", "consist");
		vcat.put("comprised", "consist");
		vcat.put("constitute", "consist");
		vcat.put("comprise", "consist");
		vcat.put("make-up", "consist");
		vcat.put("made-up-4", "consist");
		vcat.put("has", "containment");
		vcat.put("has", "containment");
		vcat.put("is", "IS-A");
		vcat.put("was", "IS-A");
		vcat.put("are", "IS-A");
		vcat.put("were", "IS-A");
		vcat.put("am", "IS-A");
		vcat.put("regarded as", "IS-A");
		vcat.put("be", "IS-A");
		vcat.put("been", "IS-A");
	}

	private static String getvcat(String verb) {
		return (vcat.get(verb) == null) ? "Other" : vcat.get(verb);
	}
}
