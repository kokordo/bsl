package org.boilit.bsl.core.eao;

import org.boilit.bsl.core.AbstractAssignOperator;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.Operation;

/**
 * @author Boilit
 * @see
 */
public final class BitXorAssign extends AbstractAssignOperator {
    public BitXorAssign(final int line, final int column, final String label, final AbstractExpression expression) {
        super(line, column, label, expression);
    }

    @Override
    protected final Object executeFragment(final Object value1, final Object value2) throws Exception {
        return Operation.doBitXor(this, value1, value2);
    }
}
