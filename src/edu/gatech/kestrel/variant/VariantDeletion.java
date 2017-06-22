// Copyright (c) 2017 Peter A. Audano III
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation; either version 3 of the License or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Library General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program; see the file COPYING.LESSER.  If not, see
// <http://www.gnu.org/licenses/>

package edu.gatech.kestrel.variant;

import edu.gatech.kestrel.activeregion.Haplotype;

/**
 * Represents an deletion variant.
 */
public class VariantDeletion extends VariantCall {
	
	/** Number of deleted bases. */
	public final int length;
	
	/**
	 * Create a variant call.
	 * 
	 * @param start Start position of this variant.
	 * @param variantDepth Depth of the haplotypes including the variant.
	 * @param locusDepth Depth of the wild-type active region and haplotypes not including
	 *   this variant.
	 * @param ref Deleted bases.
	 * @param haplotype A list of haplotypes the variant was found in.
	 * @param haploSize Number of non-null elements of <code>haplotype</code>.
	 * @param isReferenceAligned <code>true</code> if the location of this variant is relative
	 *   to the reference sequence and <code>false</code> if it is relative to a region within
	 *   the reference sequence.
	 * 
	 * @throws NullPointerException If <code>haplotype</code> or <code>ref</code> is <code>null</code>. 
	 * @throws IllegalArgumentException If <code>haplotype</code> is empty or contains <code>null</code>
	 *   references within <code>haploSize</code> elements, <code>haploSize</code> is less than <code>0</code>
	 *   or greater than <code>haplotype.length</code>, <code>start</code> is less than <code>1</code> or does
	 *   not fit in the active region in the <code>haplotype</code> elements, <code>variantDepth</code> is
	 *   negative, <code>locusDepth</code> is less than <code>variantDepth</code>, all <code>haplotypes</code>
	 *   do not reference the same active region, or <code>ref</code> is an empty string.
	 */
	public VariantDeletion(int start, int variantDepth, int locusDepth, String ref, Haplotype[] haplotype, int haploSize, boolean isReferenceAligned) {
		super(VariantType.INSERTION, start, variantDepth, locusDepth, ref, "", haplotype, haploSize, isReferenceAligned);
		
		if (ref == null)
			throw new NullPointerException("Reference allele (alt) is null on a deletion variant");
		
		length = this.ref.length();
		
		if (length == 0)
			throw new IllegalArgumentException("Reference allele (ref) is empty on a deletion variant");
		
		return;
	}
	
	/**
	 * Get the location of this variant as it would appear in a VCF file.
	 * 
	 * @return VCF location of this variant.
	 */
	@Override
	public int getVcfPos() {
		return (start > 1) ? start - 1 : start;
	}
	
	/**
	 * Get the reference sequence of this variant.
	 * 
	 * @return Reference sequence of this variant.
	 */
	@Override
	public String getVcfRef() {
		
		if (start == 1)
			return ref + Character.toString((char) activeRegion.refRegion.getBase(referenceEnd() + 1));
		
		try {
			return Character.toString((char) activeRegion.refRegion.getBase(start - 1)) + ref;
			
		} catch (IllegalArgumentException ex) {
			// start - 1 is outside the bounds of the reference region (deletion at the start of the reference and no flanking bases).
			// Use the end position instead
			return ref + Character.toString((char) activeRegion.refRegion.getBase(referenceEnd() + 1));
		}
	}
	
	/**
	 * Get the alternate sequence of this variant.
	 * 
	 * @return Alternate sequence of this variant.
	 */
	@Override
	public String getVcfAlt() {
		
		if (start == 1)
			return Character.toString((char) activeRegion.refRegion.getBase(referenceEnd() + 1));
		
		try {
			return Character.toString((char) activeRegion.refRegion.getBase(start - 1));
			
		} catch (IllegalArgumentException ex) {
			// start - 1 is outside the bounds of the reference region (deletion at the start of the reference and no flanking bases).
			// Use the end position instead
			return Character.toString((char) activeRegion.refRegion.getBase(referenceEnd() + 1));
		}
	}
	
	/**
	 * Get the location on the reference where this variant ends.
	 * 
	 * @return Location on the reference where this variant ends.
	 */
	@Override
	public int referenceEnd() {
		return start + ref.length() - 1;
	}
	
	/**
	 * Get an HGVS-formatted string description of this variant.
	 * 
	 * @return String description of this variant.
	 * 
	 * @see <a href="http://www.hgvs.org/mutnomen/recs-DNA.html">http://www.hgvs.org/mutnomen/recs-DNA.html</a>
	 */
	@Override
	public String toString() {
		return String.format("%d_%ddel", start, start + length - 1, alt);
	}
}
