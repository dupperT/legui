package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.theme.Themes;

/**
 * Class represent single line non-editable text component.
 */
public class Label extends Controller implements TextComponent {

    /**
     * Used to hold text state of component.
     */
    private TextState textState = new TextState();

    /**
     * Default constructor. Creates label with 'Label' text.
     */
    public Label() {
        this("Label");
    }

    /**
     * Creates label with specified size and on specified position.
     *
     * @param x      x position.
     * @param y      y position.
     * @param width  label width.
     * @param height label height.
     */
    public Label(float x, float y, float width, float height) {
        this("Label", x, y, width, height);
    }

    /**
     * Creates label with specified size and on specified position.
     *
     * @param position label position.
     * @param size     label size.
     */
    public Label(Vector2f position, Vector2f size) {
        this("Label", position, size);
    }

    /**
     * Creates label with specified text.
     *
     * @param text text to set.
     */
    public Label(String text) {
        initialize(text);
    }

    /**
     * Creates label with specified text, size and on specified position.
     *
     * @param text   text to set.
     * @param x      x position.
     * @param y      y position.
     * @param width  label width.
     * @param height label height.
     */
    public Label(String text, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(text);
    }

    /**
     * Creates label with specified text, size and on specified position.
     *
     * @param text     text to set.
     * @param position label position.
     * @param size     label size.
     */
    public Label(String text, Vector2f position, Vector2f size) {
        super(position, size);
        initialize(text);
    }

    /**
     * Used to initialize label.
     *
     * @param text text to set.
     */
    private void initialize(String text) {
        textState.setText(text);
        setBackgroundColor(ColorConstants.transparent());
        setBorder(null);
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(Label.class).applyAll(this);
    }

    /**
     * Returns current text state.
     *
     * @return text state of component.
     */
    public TextState getTextState() {
        return textState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Label label = (Label) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(textState, label.textState)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(textState)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("textState", textState)
                .toString();
    }

}
