package org.clulab.processors.corenlp

import org.clulab.discourse.rstparser.DiscourseTree
import org.clulab.processors.{Document, Sentence}
import edu.stanford.nlp.pipeline.Annotation
import edu.stanford.nlp.util.CoreMap
import org.clulab.struct.CorefChains
import scala.collection.JavaConverters._


/**
 * 
 * User: mihais
 * Date: 3/2/13
 */
class CoreNLPDocument(sentences: Array[Sentence]) extends Document(sentences) {

  var annotation:Option[Annotation] = None

  override def clear() {
    //println("Clearing state from document.")
    annotation = None
  }
}

object CoreNLPDocument {

  def apply(sentences: Array[Sentence]) = new CoreNLPDocument(sentences)
  def apply(
    sentences: Array[Sentence],
    coref: Option[CorefChains],
    dtree: Option[DiscourseTree],
    annotation: Option[Annotation],
    text: Option[String]
  ): CoreNLPDocument = {
    val coreDoc = new CoreNLPDocument(sentences)
    coreDoc.coreferenceChains = coref
    coreDoc.discourseTree = dtree
    coreDoc.annotation = annotation
    coreDoc
  }

  def apply(
    sentences: Array[Sentence],
    annotation: Annotation
  ): CoreNLPDocument = {
    val coreDoc = new CoreNLPDocument(sentences)
    coreDoc.annotation = Some(annotation)
    coreDoc
  }

  def fromSentences(sentences: Seq[Sentence]): CoreNLPDocument = {
    val doc = Document(sentences.toArray)
    fromDocument(doc)
  }

  def fromDocument(doc: Document): CoreNLPDocument = {
    val coreDoc = CoreNLPDocument(doc.sentences)
    coreDoc.annotation = Some(CoreNLPUtils.docToAnnotation(doc))
    coreDoc
  }
}