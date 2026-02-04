package ing.boykiss.inventoryoverhaul.client.gui.widget.config;

import ing.boykiss.inventoryoverhaul.InventoryOverhaul;
import ing.boykiss.inventoryoverhaul.client.config.annotations.ConfigOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Checkbox;

public class BooleanCheckbox implements ConfigCheckbox {
    private final ConfigOption.WidgetSize size;
    private final Checkbox widget;

    public BooleanCheckbox(WidgetData widgetData) throws IllegalAccessException {
        size = widgetData.configOption().size();
        boolean currentValue = (boolean) widgetData.field().get(widgetData.clientConfig());
        widget = Checkbox.builder(ConfigWidget.getWidgetText(widgetData.field().getName()), Minecraft.getInstance().font)
                .selected(currentValue)
                .maxWidth(widgetData.configOption().size().getSize())
                .onValueChange((checkbox, selected) -> {
                    try {
                        widgetData.field().set(widgetData.clientConfig(), selected);

                        widgetData.clientConfig().save();
                    } catch (IllegalAccessException e) {
                        InventoryOverhaul.LOGGER.error(e.getMessage());
                    }
                })
                .build();
    }

    @Override
    public ConfigOption.WidgetSize getSize() {
        return size;
    }

    @Override
    public Checkbox getWidget() {
        return widget;
    }
}
