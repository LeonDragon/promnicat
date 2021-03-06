package de.uni_potsdam.hpi.bpt.promnicat.processModelAlignment.label.similarity.matrix;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.jbpt.alignment.IEntity;

public class LinkedSimilarityMatrix<Entity extends IEntity> implements ISimilarityMatrix<Entity> {
	private LinkedHashSet<Entity> firstSet = new LinkedHashSet<Entity>();
	private LinkedHashSet<Entity> secondSet = new LinkedHashSet<Entity>();
	private LinkedHashSet<SimilarityPair<Entity>> similarities = new LinkedHashSet<SimilarityPair<Entity>>();
	/** A minimum degree of similarity. Values below will be set to 0. */
	private float threshold;

	/** creates an empty matrix with no similarity threshold */
	public LinkedSimilarityMatrix() {
		this(0);
	}
	
	/**
	 * creates an empty matrix
	 * @param threshold @see {@link #threshold}
	 */
	public LinkedSimilarityMatrix(float threshold) {
		this.setThreshold(threshold);
	}

	/** transforms the given (Entity,Entity,Similarity) relations into a new matrix without a threshold */
	public LinkedSimilarityMatrix(HashMap<Entity, HashMap<Entity, Float>> result) {
		this(result, 0);
	}
	
	/** transforms the given (Entity,Entity,Similarity) relations into a new matrix
	 * @param threshold @see {@link #threshold} */
	public LinkedSimilarityMatrix(HashMap<Entity, HashMap<Entity, Float>> result, float threshold) {
		this(threshold);
		firstSet.addAll(result.keySet());
		for (Entry<Entity, HashMap<Entity, Float>> eachEntry : result.entrySet()) {
			getSecondSet().addAll(eachEntry.getValue().keySet());
			for (Entry<Entity, Float> eachSubEntry : eachEntry.getValue().entrySet()) {
				Entity first = eachEntry.getKey();
				Entity second = eachSubEntry.getKey();
				Float similarity = eachSubEntry.getValue();
				addSimilarity(first, second, similarity);
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.hpi.bpt.pma.label.similarity.ISimilarityMatrix#addSimilarity(Entity, Entity, float)
	 */
	@Override
	public void addSimilarity(Entity first, Entity second, float similarity) {
		addSimilarity(new SimilarityPair<Entity>(first, second, similarity));
	}
	
	/* (non-Javadoc)
	 * @see de.hpi.bpt.pma.label.similarity.ISimilarityMatrix#addSimilarity(de.hpi.bpt.pma.label.similarity.SimilarityPair)
	 */
	@Override
	public void addSimilarity(SimilarityPair<Entity> pair) {
		if (pair.similarity < threshold) pair.similarity = 0;
		firstSet.add(pair.first);
		secondSet.add(pair.second);
		getSimilarities().add(pair);
	}

	/* (non-Javadoc)
	 * @see de.hpi.bpt.pma.label.similarity.ISimilarityMatrix#getSimilarities(Entity, Entity)
	 */
	@Override
	public LinkedHashSet<SimilarityPair<Entity>> getSimilarities(Entity first, Entity second) {
		if (first == null && second == null)
			return similarities;
		
		LinkedHashSet<SimilarityPair<Entity>> result = new LinkedHashSet<SimilarityPair<Entity>>();
		for (SimilarityPair<Entity> pair : similarities) {
			if ((first == null || first.equals(pair.first))
					&& (second == null || second.equals(pair.second))) {
				result.add(pair);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see de.hpi.bpt.pma.label.similarity.ISimilarityMatrix#getFirstSet()
	 */
	@Override
	public LinkedHashSet<Entity> getFirstSet() {
		return firstSet;
	}

	/* (non-Javadoc)
	 * @see de.hpi.bpt.pma.label.similarity.ISimilarityMatrix#setFirstSet(java.util.Set)
	 */
	@Override
	public void setFirstSet(Set<Entity> firstSet) {
		this.firstSet = new LinkedHashSet<Entity>(firstSet);
	}

	/* (non-Javadoc)
	 * @see de.hpi.bpt.pma.label.similarity.ISimilarityMatrix#getSecondSet()
	 */
	@Override
	public LinkedHashSet<Entity> getSecondSet() {
		return secondSet;
	}

	/* (non-Javadoc)
	 * @see de.hpi.bpt.pma.label.similarity.ISimilarityMatrix#setSecondSet(java.util.Set)
	 */
	@Override
	public void setSecondSet(Set<Entity> secondSet) {
		this.secondSet = new LinkedHashSet<Entity>(secondSet);
	}

	/* (non-Javadoc)
	 * @see de.hpi.bpt.pma.label.similarity.ISimilarityMatrix#getThreshold()
	 */
	@Override
	public float getThreshold() {
		return threshold;
	}

	/* (non-Javadoc)
	 * @see de.hpi.bpt.pma.label.similarity.ISimilarityMatrix#setThreshold(float)
	 */
	@Override
	public void setThreshold(float threshold) {
		this.threshold = threshold;
		for (SimilarityPair<Entity> pair : getSimilarities()) {
			if (pair.similarity < threshold) {
				pair.similarity = 0;
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.hpi.bpt.pma.label.similarity.ISimilarityMatrix#getSimilarities()
	 */
	@Override
	public LinkedHashSet<SimilarityPair<Entity>> getSimilarities() {
		return similarities;
	}

	/* (non-Javadoc)
	 * @see de.hpi.bpt.pma.label.similarity.ISimilarityMatrix#setSimilarities(java.util.HashSet)
	 */
	@Override
	public void setSimilarities(Set<SimilarityPair<Entity>> similarities) {
		this.similarities = new LinkedHashSet<SimilarityPair<Entity>>(similarities);
	}

	/* (non-Javadoc)
	 * @see de.hpi.bpt.pma.label.similarity.ISimilarityMatrix#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String nl = "\n";
		for (SimilarityPair<Entity> each : similarities) {
			sb.append(each).append(nl);
		}
		return sb.toString(); 
	}

	@Override
	public void removeSimilarities(Set<SimilarityPair<Entity>> toRemove) {
		similarities.removeAll(toRemove);
	}

	@Override
	public float getSimilarity() {
		float sum = 0;
		for (SimilarityPair<Entity> each : getSimilarities()) {
			sum += each.similarity;
		}
		return sum / Math.max(firstSet.size(), secondSet.size());
	}
}
