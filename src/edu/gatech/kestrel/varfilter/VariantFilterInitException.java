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

package edu.gatech.kestrel.varfilter;

/**
 * Thrown when a variant filter cannot be found or initialized.
 */
public class VariantFilterInitException extends Exception {
	
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create a new variant filter initialization exception.
	 * 
	 * @param msg Message about why this exception was thrown.
	 */
	public VariantFilterInitException(String msg) {
		super(msg);

		return;
	}

	/**
	 * Create a new variant filter initialization exception.
	 * 
	 * @param msg Message about why this exception was thrown.
	 * @param cause Cause of this exception.
	 */
	public VariantFilterInitException(String msg, Throwable cause) {
		super(msg, cause);

		return;
	}
}
