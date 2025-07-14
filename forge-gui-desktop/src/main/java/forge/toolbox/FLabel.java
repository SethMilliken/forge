package forge.toolbox;

import forge.gui.UiCommand;
import forge.gui.framework.ILocalRepaint;
import forge.gui.interfaces.IButton;
import forge.localinstance.skin.FSkinProp;
import forge.toolbox.FSkin.Colors;
import forge.toolbox.FSkin.SkinColor;
import forge.toolbox.FSkin.SkinImage;
import forge.toolbox.FSkin.SkinnedLabel;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.event.*;

/**
 * {@link FLabel} is a skinnable {@link javax.swing.JLabel} <code>Component</code>that serves as
 * a fundamental building-block in the Forge desktop GUI.
 *
 * <p>Adjustable features:
 * <ul>
 *   <li>Automatic font scaling (60% size by default, can toggle on/off)
 *   <li>Automatic icon scaling (80% size by default, can toggle on/off)
 *   <li>Scale font according to height or width
 *   <li>Hoverable
 *   <li>Selectable
 *   <li>Can execute a command when clicked
 * </ul>
 *
 * @implNote {@link FLabel} instances <i>must</i> be created using an {@link FLabel.Builder}.
 */
@SuppressWarnings("serial")
public class FLabel extends SkinnedLabel implements ILocalRepaint, IButton {
    /**
     * {@link FLabel} instances <i>must</i> be created using a <code>Builder</code>.
     *
     * <p><code>build()</code> <i>must</i> be called on a <code>Builder</code>
     * to produce a new {@link FLabel} instance.
     *
     * <p>Builder uses a fluent API, allowing methods to be chained.
     *
     * <p>Example:
     * <pre><code>
     * FLabel instance = new FLabel.Builder()
     *                             .selectable(false)
     *                             .iconAlign(SwingConstants.RIGHT)
     *                             .opaque(true)
     *                             .build();
     * }
     * </code></pre>
     *
     * @see "<i>Effective Java, 3rd Edition</i>", Bloch, Joshua.
     * Chapter 2. Creating and Destroying Objects,
     * Item 2: Consider a builder when faced with many constructor parameters
     */
    public static class Builder {
        // Set FLabel default values here.
        protected double bldIconScaleFactor = 0.8;
        protected int bldFontStyle = Font.PLAIN;
        protected int bldFontSize = 14;
        protected int bldFontAlign = SwingConstants.LEFT;
        protected float bldUnhoveredAlpha = 0.7f;
        protected int bldIconAlignX = SwingConstants.LEFT;
        protected Point bldIconInsets = new Point(0, 0);

        protected boolean bldSelectable = false;
        protected boolean bldSelected = false;
        protected boolean bldHoverable = false;
        protected boolean bldOpaque = false;
        protected boolean bldIconInBackground = false;
        protected boolean bldIconScaleAuto = true;
        protected boolean bldReactOnMouseDown = false;
        protected boolean bldUseSkinColors = true;
        protected boolean bldEnabled = true;
        protected boolean bldSuppressFocusable = false;

        protected String bldText;
        protected String bldToolTip;
        protected SkinImage bldIcon;
        protected UiCommand bldCmd;

        public Builder copy() {
            Builder copy = new Builder();
            copy.bldIconScaleFactor = this.bldIconScaleFactor;
            copy.bldFontStyle = this.bldFontStyle;
            copy.bldFontSize = this.bldFontSize;
            copy.bldFontAlign = this.bldFontAlign;
            copy.bldUnhoveredAlpha = this.bldUnhoveredAlpha;
            copy.bldIconAlignX = this.bldIconAlignX;
            copy.bldIconInsets = this.bldIconInsets;
            copy.bldSelectable = this.bldSelectable;
            copy.bldSelected = this.bldSelected;
            copy.bldHoverable = this.bldHoverable;
            copy.bldOpaque = this.bldOpaque;
            copy.bldIconInBackground = this.bldIconInBackground;
            copy.bldIconScaleAuto = this.bldIconScaleAuto;
            copy.bldReactOnMouseDown = this.bldReactOnMouseDown;
            copy.bldUseSkinColors = this.bldUseSkinColors;
            copy.bldEnabled = this.bldEnabled;
            copy.bldSuppressFocusable = this.bldSuppressFocusable;
            copy.bldText = this.bldText;
            copy.bldToolTip = this.bldToolTip;
            copy.bldIcon = this.bldIcon;
            copy.bldCmd = this.bldCmd;
            return copy;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Builder builder)) return false;
            return Double.compare(bldIconScaleFactor, builder.bldIconScaleFactor) == 0 && bldFontStyle == builder.bldFontStyle && bldFontSize == builder.bldFontSize && bldFontAlign == builder.bldFontAlign && Float.compare(bldUnhoveredAlpha, builder.bldUnhoveredAlpha) == 0 && bldIconAlignX == builder.bldIconAlignX && bldSelectable == builder.bldSelectable && bldSelected == builder.bldSelected && bldHoverable == builder.bldHoverable && bldOpaque == builder.bldOpaque && bldIconInBackground == builder.bldIconInBackground && bldIconScaleAuto == builder.bldIconScaleAuto && bldReactOnMouseDown == builder.bldReactOnMouseDown && bldUseSkinColors == builder.bldUseSkinColors && bldEnabled == builder.bldEnabled && bldSuppressFocusable == builder.bldSuppressFocusable && Objects.equals(bldIconInsets, builder.bldIconInsets) && Objects.equals(bldText, builder.bldText) && Objects.equals(bldToolTip, builder.bldToolTip) && Objects.equals(bldIcon, builder.bldIcon) && Objects.equals(bldCmd, builder.bldCmd);
        }

        @Override
        public int hashCode() {
            return Objects.hash(bldIconScaleFactor, bldFontStyle, bldFontSize, bldFontAlign, bldUnhoveredAlpha, bldIconAlignX, bldIconInsets, bldSelectable, bldSelected, bldHoverable, bldOpaque, bldIconInBackground, bldIconScaleAuto, bldReactOnMouseDown, bldUseSkinColors, bldEnabled, bldSuppressFocusable, bldText, bldToolTip, bldIcon, bldCmd);
        }

        /**
         * Instantiates an {@link forge.toolbox.FLabel} using the values of the <code>Builder</code>.
         * @return {@link forge.toolbox.FLabel}
         */
        public FLabel build() {
            return new FLabel(this);
        }

        // Begin builder methods.

        /**
         * @param s0 &emsp; {@link java.lang.String}
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder text(final String s0) {
            this.bldText = s0;
            return this;
        }

        /**
         * @param s0 &emsp; {@link java.lang.String}
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder tooltip(final String s0) {
            this.bldToolTip = s0;
            return this;
        }

        /**
         * @param i0 &emsp; {@link forge.toolbox.FSkin.SkinIcon}
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder icon(final SkinImage i0) {
            this.bldIcon = i0;
            return this;
        }

        /**
         * Convenience method to wrap a commonly used pattern.
         *
         * @param prop &emsp; {@link FSkinProp} for which to get image
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder iconImage(final FSkinProp prop) {
            this.bldIcon = FSkin.getImage(prop);
            return this;
        }

        /**
         * @param i0 &emsp; SwingConstants.CENTER, .LEFT, or .RIGHT
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder fontAlign(final int i0) {
            if (i0 != SwingConstants.CENTER && i0 != SwingConstants.LEFT && i0 != SwingConstants.RIGHT) {
                throw new IllegalArgumentException(String.format("FLabel.Builder$fontAlign must be SwingConstants.CENTER, SwingConstants.LEFT, or SwingConstants.RIGHT: %s", i0));
            }
            this.bldFontAlign = i0;
            return this;
        }

        /**
         * @param b0 &emsp; boolean
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder opaque(final boolean b0) {
            this.bldOpaque = b0;
            return this;
        }

        public Builder opaque() {
            opaque(true);
            return this;
        }

        /**
         * @param b0 &emsp; boolean
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder hoverable(final boolean b0) {
            this.bldHoverable = b0;
            return this;
        }

        public Builder hoverable() {
            hoverable(true);
            return this;
        }

        /**
         * @param b0 &emsp; boolean
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder selectable(final boolean b0) {
            this.bldSelectable = b0;
            return this;
        }

        public Builder selectable() {
            selectable(true);
            return this;
        }

        /**
         * @param b0 &emsp; boolean
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder selected(final boolean b0) {
            this.bldSelected = b0;
            return this;
        }

        public Builder selected() {
            selected(true);
            return this;
        }

        /**
         * @param b0 &emsp; boolean that controls when the label responds to mouse events
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder reactOnMouseDown(final boolean b0) {
            this.bldReactOnMouseDown = b0;
            return this;
        }

        public Builder reactOnMouseDown() {
            reactOnMouseDown(true);
            return this;
        }

        /**
         * @param b0 &emsp; boolean that controls whether the text uses skin colors
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder useSkinColors(final boolean b0) {
            bldUseSkinColors = b0;
            return this;
        }

        /**
         * @param c0 &emsp; {@link forge.gui.UiCommand} to execute if clicked
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder cmdClick(final UiCommand c0) {
            this.bldCmd = c0;
            return this;
        }

        /**
         * @param i0 &emsp; int
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder fontSize(final int i0) {
            this.bldFontSize = i0;
            return this;
        }

        /**
         * @param i0 &emsp; Font.PLAIN, Font.BOLD, or Font.ITALIC
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder fontStyle(final int i0) {
            if (i0 != Font.PLAIN && i0 != Font.BOLD && i0 != Font.ITALIC) {
                throw new IllegalArgumentException(String.format("FLabel.Builder$fontStyle must be Font.PLAIN, Font.BOLD, or Font.ITALIC: %s", i0));
            }
            this.bldFontStyle = i0;
            return this;
        }

        /**
         * @param b0 &emsp; boolean
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder enabled(final boolean b0) {
            this.bldEnabled = b0;
            return this;
        }

        /**
         * @param b0 &emsp; boolean
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder suppressFocusable(final boolean b0) {
            this.bldSuppressFocusable = b0;
            return this;
        }

        /**
         * @param b0 &emsp; boolean
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder iconScaleAuto(final boolean b0) {
            this.bldIconScaleAuto = b0;
            return this;
        }

        /**
         * @param d0 &emsp; double between 0 and 1; 0.8 by default
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder iconScaleFactor(final double d0) {
            if (d0 < 0.0 || d0 > 1.0) {
                throw new IllegalArgumentException(String.format("FLabel.Builder$iconScaleFactor must be between 0.0 and 1.0: %s", d0));
            }
            this.bldIconScaleFactor = d0;
            return this;
        }

        /**
         * @param b0 &emsp; boolean, icon will be drawn independent of text
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder iconInBackground(final boolean b0) {
            this.bldIconInBackground = b0;
            return this;
        }

        public Builder iconInBackground() {
            iconInBackground(true);
            return this;
        }

        /**
         * @param f0 &emsp; 0.0f - 1.0f. alpha factor applied when label is hoverable but not currently hovered.
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder unhoveredAlpha(final float f0) {
            if (f0 < 0.0f || f0 > 1.0f) {
                throw new IllegalArgumentException(String.format("FLabel.Builder$iconScaleFactor must be between 0.0f and 1.0f: %s", f0));
            }
            this.bldUnhoveredAlpha = f0;
            return this;
        }

        /**
         * @param i0 &emsp; Int. Only available for background icon.
         *           SwingConstants.HORIZONTAL .VERTICAL or .CENTER
         * @return {@link forge.toolbox.FLabel.Builder}
         */
        public Builder iconAlignX(final int i0) {
            this.bldIconAlignX = i0;
            return this;
        }
    }

    // sets better defaults for button labels
    public static class ButtonBuilder extends Builder {
        public ButtonBuilder() {
            hoverable(true);
            opaque(true);
        }
    }

    //========== Constructors

    /**
     * @implNote Must be called <i>only</i> by {@link FLabel.Builder}.
     */
    protected FLabel(final Builder b0) {
        super(b0.bldText);

        // Init fields from builder
        this.iconScaleFactor = b0.bldIconScaleFactor;

        this.opaque = b0.bldOpaque;
        this.iconInBackground = b0.bldIconInBackground;
        this.iconScaleAuto = b0.bldIconScaleAuto;
        this.selectable = b0.bldSelectable;
        this.selected = b0.bldSelected;
        this.iconAlignX = b0.bldIconAlignX;
        this.iconInsets = b0.bldIconInsets;
        this.fontStyle = b0.bldFontStyle;

        this.setEnabled(b0.bldEnabled);
        this.setFontSize(b0.bldFontSize);
        this.setUnhoveredAlpha(b0.bldUnhoveredAlpha);
        this.setCommand(b0.bldCmd);
        this.setReactOnMouseDown(b0.bldReactOnMouseDown);
        this.setHorizontalAlignment(b0.bldFontAlign);
        this.setToolTipText(b0.bldToolTip);
        this.setHoverable(b0.bldHoverable);

        // Set this only after setting all other properties that affect the icon.
        this.setIcon(b0.bldIcon);

        // If the label has button-like properties, interpret keypresses like a button
        if (!b0.bldSuppressFocusable || b0.bldSelectable || b0.bldHoverable) {
            this.setFocusable(true);

            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(final KeyEvent e) {
                    if (e.getKeyChar() == ' ' || e.getKeyCode() == 10 || e.getKeyCode() == KeyEvent.VK_ENTER) {
                        _doMouseAction();
                    }
                }
            });

            this.addFocusListener(new FocusListener() {
                @Override
                public void focusLost(final FocusEvent arg0) {
                    repaintSelf();
                }

                @Override
                public void focusGained(final FocusEvent arg0) {
                    repaintSelf();
                }
            });
        } else {
            this.setFocusable(false);
        }

        if (b0.bldUseSkinColors) {
            // Non-custom display properties
            this.setForeground(clrText);
            this.setBackground(clrMain);
        }

        // Resize adapter
        this.removeComponentListener(cadResize);
        this.addComponentListener(cadResize);

        // First-time-shown adapter (required to size icons properly
        // if icon is set while the label is still 0 x 0)
        this.addAncestorListener(showFirstTime);
    }

    //========== Variable initialization
    // Final inits
    private static final SkinColor clrHover = FSkin.getColor(FSkin.Colors.CLR_HOVER);
    private static final SkinColor clrText = FSkin.getColor(FSkin.Colors.CLR_TEXT);
    private static final SkinColor clrMain = FSkin.getColor(FSkin.Colors.CLR_INACTIVE);
    private static final SkinColor d50 = clrMain.stepColor(-50);
    private static final SkinColor d30 = clrMain.stepColor(-30);
    private static final SkinColor d10 = clrMain.stepColor(-10);
    private static final SkinColor l10 = clrMain.stepColor(10);
    private static final SkinColor l20 = clrMain.stepColor(20);
    private static final SkinColor l30 = clrMain.stepColor(30);

    // Custom properties, assigned either at realization (using builder)
    // or dynamically (using methods below).
    private final double iconScaleFactor;
    private int fontStyle;
    private final int iconAlignX;
    private int iw, ih;
    private final boolean selectable;
    private boolean selected;
    private boolean hoverable;
    private boolean hovered;
    private boolean pressed;
    private boolean opaque;
    private final boolean iconInBackground;
    private final boolean iconScaleAuto;
    private boolean reactOnMouseDown;
    private final Point iconInsets;

    // Various variables used in image rendering.
    private Image img;

    private Runnable cmdClick, cmdRightClick;

    private double iar;

    private AlphaComposite alphaDim, alphaStrong;

    private final ActionListener fireResize = new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent evt) {
            resetIcon();
            resizeTimer.stop();
        }
    };

    private final Timer resizeTimer = new Timer(10, fireResize);

    // Resize adapter; on a timer to prevent resizing while "sliding" between sizes
    private final ComponentAdapter cadResize = new ComponentAdapter() {
        @Override
        public void componentResized(final ComponentEvent e) {
            resizeTimer.restart();
        }
    };

    private final AncestorListener showFirstTime = new AncestorListener() {
        @Override
        public void ancestorAdded(final AncestorEvent e) {
            resetIcon();
        }

        @Override
        public void ancestorMoved(final AncestorEvent arg0) {
        }

        @Override
        public void ancestorRemoved(final AncestorEvent arg0) {
        }
    };

    private void _doMouseAction() {
        if (selectable) { setSelected(!selected); }
        if (cmdClick != null && isEnabled()) {
            cmdClick.run();
        }
    }

    private void _doRightClickAction() {
        if (cmdRightClick != null && isEnabled()) {
            cmdRightClick.run();
        }
    }

    // Mouse event handler
    private final FMouseAdapter madEvents = new FMouseAdapter() {
        @Override
        public void onMouseEnter(final MouseEvent e) {
            setHovered(true);
        }

        @Override
        public void onMouseExit(final MouseEvent e) {
            setHovered(false);
        }

        @Override
        public void onLeftMouseDown(final MouseEvent e) {
            if (reactOnMouseDown) {
                _doMouseAction(); //for best responsiveness, do action before repainting for pressed state
            }
            setPressed(true);
        }

        @Override
        public void onLeftMouseUp(final MouseEvent e) {
            setPressed(false);
        }

        @Override
        public void onLeftClick(final MouseEvent e) {
            if (!reactOnMouseDown) {
                _doMouseAction();
            }
        }

        @Override
        public void onRightClick(final MouseEvent e) {
            _doRightClickAction();
        }
    };

    //========== Methods

    /**
     * @param b0 &emsp; boolean
     * @implNote Must be public.
     */
    @Override
    public void setEnabled(final boolean b0) {
        if (this.isEnabled() == b0) { return; }
        super.setEnabled(b0);
        if (!this.hoverable) { return; }
        if (!b0) { this.removeMouseListener(madEvents); }
        else { this.addMouseListener(madEvents); }
    }

    /**
     * @param b0 &emsp; boolean
     * @implNote Must be public.
     */
    public void setHoverable(final boolean b0) {
        if (this.hoverable == b0) { return; }
        this.hoverable = b0;
        if (!this.isEnabled()) { return; }
        if (!b0) { this.removeMouseListener(madEvents); }
        else { this.addMouseListener(madEvents); }
    }

    protected void setHovered(final boolean hovered0) {
        this.hovered = hovered0;
        repaintSelf();
    }

    protected void setPressed(final boolean pressed0) {
        this.pressed = pressed0;
        repaintSelf();
    }

    /**
     * @param b0 &emsp; boolean
     * @implNote Must be public.
     */
    @Override
    public void setSelected(final boolean b0) {
        this.selected = b0;
        repaintSelf();
    }

    @Override
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * Sets alpha if icon is in background.
     *
     * @param f0 float
     * @implNote Must be called <i>only</i> when initially instantiated.
     */
    private void setUnhoveredAlpha(final float f0) {
        this.alphaDim = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f0);
        this.alphaStrong = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
    }

    public void setFontSize(final int i0) {
        switch (this.fontStyle) {
            case Font.BOLD:
                this.setFont(FSkin.getBoldFont(i0));
                break;
            case Font.ITALIC:
                this.setFont(FSkin.getItalicFont(i0));
                break;
            default:
                this.setFont(FSkin.getFont(i0));
        }
    }

    public int getAutoSizeWidth() {
        int width = 0;
        if (this.getText() != null && !this.getText().isEmpty()) {
            final FontMetrics metrics = this.getFontMetrics(this.getFont());
            width = metrics.stringWidth(this.getText());
        }
        if (this.getIcon() != null) {
            width += this.getIcon().getIconWidth() + this.getIconTextGap();
        }
        if (opaque || selectable) {
            width += 6; //account for border/padding if opaque
        }
        return width;
    }

    /**
     * Resizing in MigLayout "slides" between the original and destination sizes.
     * To prevent this label from recalculating on each increment, a timer
     * is run to check that the "sliding" is finished.  To resize this label
     * explicitly, call this method after resize to retrieve the timer and start it;
     * it will stop automatically.
     */
    public void refresh() {
        this.resizeTimer.start();
    }

    /**
     * @return {@link forge.gui.UiCommand}
     */
public Runnable getCommand() {
        return this.cmdClick;
    }

    /**
     * @return {@link forge.gui.UiCommand}
     */
    public Runnable getRightClickCommand() {
        return this.cmdRightClick;
    }

    protected int getMaxTextWidth() {
        final int w = getWidth();
        final int h = getHeight();
        final int sh = (int) (h * iconScaleFactor);
        final int sw = (int) (sh * iar);
        return w - sw;
    }

    @Override
    /** @implNote Must be public */
    public void setIcon(final Icon i0) {
        // Will need image (not icon) for scaled and non-scaled.
        // Will need image if not in background, but scaled.
        if (iconInBackground || iconScaleAuto) {
            if (i0 != null) {
                img = ((ImageIcon) i0).getImage();
                iw = img.getWidth(null);
                ih = img.getHeight(null);
                iar = ((double) iw) / ((double) ih);
            } else {
                img = null;
                iw = 0;
                ih = 0;
                iar = 0;
            }
        } else { // If not in background, not scaled, can use original icon.
            super.setIcon(i0);
        }
    }

    /**
     * @param c0 &emsp; {@link forge.gui.UiCommand} on click
     */
    public void setCommand(final Runnable c0) {
        this.cmdClick = c0;
    }

    /**
     * @param c0 &emsp; {@link forge.gui.UiCommand} on right-click
     */
    public void setRightClickCommand(final Runnable c0) {
        this.cmdRightClick = c0;
    }

    public void setReactOnMouseDown(final boolean b0) {
        this.reactOnMouseDown = b0;
    }

    @Override
    public void setOpaque(final boolean b0) {
        // Must be overridden to allow drawing order of background, icon, string
        this.opaque = b0;
        super.setOpaque(false);
    }

    /**
     * Major performance kicker - won't repaint whole screen!
     */
    @Override
    public void repaintSelf() {
        final Dimension d = getSize();
        repaint(0, 0, d.width, d.height);
    }

    @Override
    public void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;

        final int w = getWidth();
        final int h = getHeight();

        final boolean paintWithHover = hoverable && hovered && isEnabled();
        final Composite oldComp = g2d.getComposite();
        if (hoverable) {
            g2d.setComposite(paintWithHover ? alphaStrong : alphaDim);
        }

        final boolean paintPressedState = pressed && hovered && isEnabled() && (opaque || selectable);
        if (paintPressedState) {
            paintPressed(g2d, w, h);
        } else if (opaque) {
            if (selected) {
                paintDown(g2d, w, h);
            } else {
                paintUp(g2d, w, h);
            }
        } else if (selectable) {
            if (selected) {
                paintDown(g2d, w, h);
            } else {
                paintBorder(g2d, w, h);
            }
        }

        paintContent(g2d, w, h, paintPressedState);

        if (hoverable) {
            g2d.setComposite(oldComp);
        }

        if (hasFocus() && isEnabled()) {
            paintFocus(g2d, w, h);
        }
    }

    protected void paintContent(final Graphics2D g, final int w, final int h, final boolean paintPressedState) {
        if (paintPressedState) { //while pressed, translate graphics so icon and text appear shifted down and to the right
            g.translate(1, 1);
        }

        // Icon in background
        if (iconInBackground) {
            final int sh = (int) (h * iconScaleFactor);
            final int sw = (int) (sh * iar);

            final int x = iconAlignX == SwingConstants.CENTER
                    ? (int) ((w - sw) / 2 + iconInsets.getX())
                    : (int) iconInsets.getX();

            final int y = (int) (((h - sh) / 2) + iconInsets.getY());

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.drawImage(img, x, y, sw + x, sh + y, 0, 0, iw, ih, null);
        }

        super.paintComponent(g);

        if (paintPressedState) { //reset translation after icon and text painted
            g.translate(-1, -1);
        }
    }

    private static void paintFocus(final Graphics2D g, final int w, final int h) {
        FSkin.setGraphicsColor(g, clrHover);
        g.drawRect(0, 0, w - 2, h - 2);
        FSkin.setGraphicsColor(g, l30);
        g.drawRect(1, 1, w - 4, h - 4);
    }

    private static void paintPressed(final Graphics2D g, final int w, final int h) {
        FSkin.setGraphicsGradientPaint(g, 0, h, d50, 0, 0, d10);
        g.fillRect(0, 0, w - 1, h - 1);

        FSkin.setGraphicsColor(g, d50);
        g.drawRect(0, 0, w - 2, h - 2);
        FSkin.setGraphicsColor(g, d10);
        g.drawRect(1, 1, w - 4, h - 4);
    }

    private static void paintUp(final Graphics2D g, final int w, final int h) {
        FSkin.setGraphicsGradientPaint(g, 0, h, d10, 0, 0, l20);
        g.fillRect(0, 0, w, h);

        FSkin.setGraphicsColor(g, d50);
        g.drawRect(0, 0, w - 2, h - 2);
        FSkin.setGraphicsColor(g, l10);
        g.drawRect(1, 1, w - 4, h - 4);
    }

    private static void paintBorder(final Graphics2D g, final int w, final int h) {
        FSkin.setGraphicsColor(g, l10);
        g.drawRect(0, 0, w - 2, h - 2);
        FSkin.setGraphicsColor(g, l30);
        g.drawRect(1, 1, w - 4, h - 4);
    }

    private static void paintDown(final Graphics2D g, final int w, final int h) {
        FSkin.setGraphicsGradientPaint(g, 0, h, d30, 0, 0, l10);
        g.fillRect(0, 0, w - 1, h - 1);

        FSkin.setGraphicsColor(g, d30);
        g.drawRect(0, 0, w - 2, h - 2);
        FSkin.setGraphicsColor(g, l10);
        g.drawRect(1, 1, w - 4, h - 4);
    }

    protected void resetIcon() {
        // Non-background icon
        if (img != null && iconScaleAuto && !iconInBackground) {
            final int h = (int) (getHeight() * iconScaleFactor);
            final int w = (int) (h * iar);
            if (w == 0 || h == 0) { return; }

            super.setIcon(new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH)));
        }
    }

    @Override
    public void setCommand(final UiCommand command0) {
        cmdClick = command0;
    }

    @Override
    public void setImage(final FSkinProp color) {
        setForeground(FSkin.getColor(Colors.fromSkinProp(color)));
    }

    @Override
    public void setTextColor(final int r, final int g, final int b) {
        setForeground(new Color(r, g, b));
    }

}
