package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.event.label.LabelWidthChangeEvent;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;

import static org.liquidengine.legui.style.util.StyleUtilities.*;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.calculateTextBoundsRect;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgLabelRenderer extends NvgDefaultComponentRenderer<Label> {

    @Override
    public void renderSelf(Label label, Context context, long nanovg) {
        createScissor(nanovg, label);
        {
            Style style = label.getStyle();
            Vector2f pos = label.getAbsolutePosition();
            Vector2f size = label.getSize();

            /*Draw background rectangle*/
            renderBackground(label, context, nanovg);

            // draw text into box
            TextState textState = label.getTextState();
            Vector4f padding = getPadding(label, style);
            Vector4f rect = getInnerContentRectangle(pos, size, padding);
            Float fontSize = getStyle(label, Style::getFontSize, 16F);
            VerticalAlign verticalAlign = getStyle(label, Style::getVerticalAlign, VerticalAlign.MIDDLE);
            HorizontalAlign horizontalAlign = getStyle(label, Style::getHorizontalAlign, HorizontalAlign.LEFT);
            NvgText.drawTextLineToRect(nanovg, rect, false,
                horizontalAlign, verticalAlign, fontSize,
                getStyle(label, Style::getFont, FontRegistry.getDefaultFont()),
                textState.getText(),
                getStyle(label, Style::getTextColor),
                label.getTextDirection());

            float[] textBounds = calculateTextBoundsRect(nanovg, rect, textState.getText(), horizontalAlign, verticalAlign, fontSize);
            float textWidth = textState.getTextWidth();

            textState.setTextWidth(textBounds[2]);
            textState.setTextHeight(fontSize);
            textState.setCaretX(null);
            textState.setCaretY(null);

            if (Math.abs(textWidth - textBounds[2]) > 0.001) {
                EventProcessorProvider.getInstance().pushEvent(new LabelWidthChangeEvent(label, context, label.getFrame(), textBounds[2]));
            }

        }
        resetScissor(nanovg);
    }
}
