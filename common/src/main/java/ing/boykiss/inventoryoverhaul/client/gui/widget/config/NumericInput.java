package ing.boykiss.inventoryoverhaul.client.gui.widget.config;

import ing.boykiss.inventoryoverhaul.InventoryOverhaul;
import ing.boykiss.inventoryoverhaul.client.config.annotations.ConfigOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;

public class NumericInput implements ConfigInput {
    private final ConfigOption.WidgetSize size;
    private final EditBox widget;

    public NumericInput(WidgetData widgetData, double min, double max) throws IllegalAccessException {
        size = widgetData.configOption().size();
        double value = Double.parseDouble(widgetData.field().get(widgetData.clientConfig()).toString());
        boolean isIntegral = widgetData.type() == short.class || widgetData.type() == Short.class ||
                widgetData.type() == int.class || widgetData.type() == Integer.class ||
                widgetData.type() == long.class || widgetData.type() == Long.class;

        widget = new EditBox(Minecraft.getInstance().font, widgetData.configOption().size().getSize(), WIDGET_HEIGHT, ConfigWidget.getWidgetText(widgetData.field().getName()));
        if (isIntegral) {
            widget.setValue(Long.toString((long) value));
        } else {
            widget.setValue(Double.toString(value));
        }

        widget.setResponder((text) -> {
            try {
                double parsedValue;
                try {
                    parsedValue = Double.parseDouble(text);
                } catch (NumberFormatException e) {
                    return;
                }

                if (!Double.isNaN(min) && parsedValue < min) return;
                if (!Double.isNaN(max) && parsedValue > max) return;

                if (isIntegral) {
                    if (widgetData.type() == short.class || widgetData.type() == Short.class) {
                        widgetData.field().set(widgetData.clientConfig(), (short) parsedValue);
                    } else if (widgetData.type() == int.class || widgetData.type() == Integer.class) {
                        widgetData.field().set(widgetData.clientConfig(), (int) parsedValue);
                    } else {
                        widgetData.field().set(widgetData.clientConfig(), (long) parsedValue);
                    }
                } else {
                    if (widgetData.type() == float.class || widgetData.type() == Float.class) {
                        widgetData.field().set(widgetData.clientConfig(), (float) parsedValue);
                    } else {
                        widgetData.field().set(widgetData.clientConfig(), parsedValue);
                    }
                }

                widgetData.clientConfig().save();
            } catch (IllegalAccessException e) {
                InventoryOverhaul.LOGGER.error(e.getMessage());
            }
        });
    }

    @Override
    public ConfigOption.WidgetSize getSize() {
        return size;
    }

    @Override
    public EditBox getWidget() {
        return widget;
    }
}
