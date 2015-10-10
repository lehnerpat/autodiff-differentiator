/**
 * ISC License Terms (http://opensource.org/licenses/isc-license):
 *
 * Copyright (c) 2015, Patrick Lehner <lehner dot patrick at gmx dot de>
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby
 * granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN
 * AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
 * PERFORMANCE OF THIS SOFTWARE.
 */

package nevik.autodiff.expr.real.visitor;

import nevik.autodiff.expr.real.RealVariable;
import org.junit.Before;
import org.junit.Test;

import static nevik.autodiff.expr.real.RealConstant.ONE;
import static nevik.autodiff.expr.real.RealConstant.ZERO;
import static nevik.autodiff.expr.real.RealConstant.reCons;
import static nevik.autodiff.expr.real.RealExprAddition.reAdd;
import static nevik.autodiff.expr.real.RealExprMultiplication.reMult;
import static nevik.autodiff.expr.real.RealExprNegation.reNeg;
import static nevik.autodiff.expr.real.RealExprReciprocal.reRecip;
import static nevik.autodiff.expr.real.visitor.VisitorRealExpressionPartialDifferentiator.differentiate;
import static org.junit.Assert.assertEquals;

/**
 * @author Patrick Lehner
 * @since 2015-10-10
 */
public class VisitorRealExpressionPartialDifferentiatorTest {
	private RealVariable x, y, z;

	@Before
	public void setUp() throws Exception {
		x = new RealVariable("x");
		y = new RealVariable("y");
		z = new RealVariable("z");
	}

	@Test
	public void testDiffConstant() throws Exception {
		assertEquals(ZERO, differentiate(ONE, x));
	}

	@Test
	public void testDiffSameVariable() throws Exception {
		assertEquals(ONE, differentiate(x, x));
	}

	@Test
	public void testDiffDifferentVariable() throws Exception {
		assertEquals(ZERO, differentiate(y, x));
	}

	@Test
	public void testDiffSum() throws Exception {
		assertEquals(ONE, differentiate(reAdd(ONE, x), x));
		assertEquals(reAdd(ONE, ONE), differentiate(reAdd(x, x), x));
	}

	@Test
	public void testDiffNegation() throws Exception {
		assertEquals(ZERO, differentiate(reNeg(ONE), x));
		assertEquals(reNeg(ONE), differentiate(reNeg(x), x));
	}

	@Test
	public void testDiffMultiplication() throws Exception {
		assertEquals(reCons(2), differentiate(reMult(reCons(2), x), x));
		assertEquals(reAdd(x, x), differentiate(reMult(x, x), x));
	}

	@Test
	public void testDiffReciprocal() throws Exception {
		assertEquals(reNeg(reRecip(reMult(x,x))), differentiate(reRecip(x), x));
	}
}