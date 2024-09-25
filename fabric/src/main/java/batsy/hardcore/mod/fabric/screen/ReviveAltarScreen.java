package batsy.hardcore.mod.fabric.screen;

import batsy.hardcore.mod.util.BatsyHardcoreUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ReviveAltarScreen extends HandledScreen<ReviveAltarScreenHandler> {
    private final Identifier backgroundTexture;
    private final Identifier loadedTexture;

    public ReviveAltarScreen(ReviveAltarScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        backgroundTexture = BatsyHardcoreUtil.id("textures/gui/revive_altar_gui.png");
        loadedTexture = BatsyHardcoreUtil.id("textures/item/revive_totem_advanced.png");
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(@NotNull DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, backgroundTexture);
        Point offset = calculateBackgroundOffset();
        context.drawTexture(backgroundTexture, offset.x, offset.y, 0, 0, backgroundWidth, backgroundHeight);
        renderProgressArrow(context, offset.x, offset.y);
        renderLoaded(context);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isLoading()) {
            Point arrowPos = calculateArrowPosition(x, y);
            context.drawTexture(backgroundTexture, arrowPos.x, arrowPos.y, 176, 0, 8, handler.getScaledLoadingProgress());
        }
    }

    private void renderLoaded(DrawContext context) {
        if (handler.isLoaded()) {
            context.drawTexture(loadedTexture, 80, 59, 0, 0, 16, 16);
        }
    }

    private @NotNull Point calculateBackgroundOffset() {
        return new Point((width - backgroundWidth) / 2, (height - backgroundHeight) / 2);
    }

    private @NotNull Point calculateArrowPosition(int x, int y) {
        return new Point(x + 85, y + 30);
    }
}
