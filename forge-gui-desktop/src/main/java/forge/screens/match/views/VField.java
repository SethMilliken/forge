/*
 * Forge: Play Magic: the Gathering.
 * Copyright (C) 2011  Forge Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package forge.screens.match.views;

import forge.game.card.CounterEnumType;
import forge.game.player.PlayerView;
import forge.game.zone.ZoneType;
import forge.gui.framework.DragCell;
import forge.gui.framework.DragTab;
import forge.gui.framework.EDocID;
import forge.gui.framework.IVDoc;
import forge.localinstance.skin.FSkinProp;
import forge.screens.match.CMatchUI;
import forge.screens.match.controllers.CField;
import forge.toolbox.FLabel;
import forge.toolbox.FScrollPane;
import forge.toolbox.FSkin;
import forge.toolbox.FSkin.SkinImage;
import forge.toolbox.FSkin.SkinnedPanel;
import forge.toolbox.special.PhaseIndicator;
import forge.toolbox.special.PlayerDetailsPanel;
import forge.util.Localizer;
import forge.view.arcane.PlayArea;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BooleanSupplier;
import javax.swing.*;
import javax.swing.border.*;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * Assembles Swing components of a player field instance.
 * 
 * @implNote A <i>V</i> prefixed class name indicates a view class.</i>
 */
public class VField implements IVDoc<CField>, Localizer.Localizable {
    private static final Logger log = LoggerFactory.getLogger(VField.class);

    private static final int DEFAULT_TEXT_GAP = DEFAULT_FONT_SIZE / 4;

    private static final int HEIGHT = DEFAULT_FONT_SIZE + (DEFAULT_TEXT_GAP * 4);

    private static final int AVATAR_MIN_WIDTH = 325;
    private static final int AVATAR_MIN_HEIGHT = AVATAR_MIN_WIDTH * 1;
    private static final int AVATAR_DEFAULT_WIDTH = AVATAR_MIN_WIDTH * 2;
    private static final int AVATAR_DEFAULT_HEIGHT = AVATAR_MIN_HEIGHT * 2;

    private static final int COUNTER_MIN_WIDTH = 175;
    private static final int COUNTER_MIN_HEIGHT = HEIGHT;

    private static final int COUNTER_PANEL_MIN_WIDTH = DEFAULT_TEXT_GAP * 10;
    private static final int COUNTER_PANEL_DEFAULT_WIDTH = COUNTER_MIN_WIDTH * 4;

    private static final int FRAME_MIN_WIDTH = Math.min(COUNTER_PANEL_MIN_WIDTH, AVATAR_MIN_WIDTH) + COUNTER_MIN_WIDTH;
    private static final int FRAME_MIN_HEIGHT = (COUNTER_MIN_HEIGHT * 2) + AVATAR_MIN_HEIGHT;

    private static final int[] DEBUG_RATE = {0};

    // Fields used with interface IVDoc
    private final CField control;
    private DragCell parentCell;
    private final EDocID docID;
    private final DragTab tab = new DragTab(localize().getMessage("lblField"));

    // Other fields
    private final PlayerView player;

    // Top-level containers
    private final AvatarArea avatarArea;
    private final PlayerDetailsPanel detailsPanel;
    private final PhaseIndicator phaseIndicator = new PhaseIndicator();
    private final PlayArea tabletop;
    private final FScrollPane scroller = new FScrollPane(false);

    /**
     * Constructor
     *
     * @param matchUI &emsp; {@link forge.screens.match.CMatchUI} associated with this <code>VField</code>.
     * @param id0 &emsp; {@link forge.gui.framework.EDocID} identifier
     * @param p &emsp; {@link forge.game.player.Player} associated with this <code>VField</code>.
     * @param mirror &emsp; passed through to {@link forge.view.arcane.PlayArea}.
     */
    public VField(final CMatchUI matchUI, final EDocID id0, final PlayerView p, final boolean mirror) {
        this.docID = id0;
        this.player = p;
        this.detailsPanel = new PlayerDetailsPanel(player, CMatchUI.FLOATING_ZONE_TYPES);

        this.control = new CField(matchUI, player, this);

        this.avatarArea = new AvatarArea(player, () -> control.getMatchUI().isHighlighted(player));

        this.tabletop = new PlayArea(matchUI, scroller, mirror, player, ZoneType.Battlefield);
        this.tabletop.setBorder(new FSkin.MatteSkinBorder(0, 1, 0, 0, FSkin.getColor(FSkin.Colors.CLR_BORDERS)));
        this.tabletop.setOpaque(false);

        this.scroller.setViewportView(this.tabletop);
        assignTabText();
    }

    private void assignTabText() {
        if (player != null) {
            tab.setText(localize().getMessage("lblPlayField", player.getName()));
        } else {
            tab.setText(localize().getMessage("lblNoPlayerForEDocID", docID.toString()));
        }
    }

    // Client Update Methods
    
    @Override
    public void populate() {
        final JPanel pnl = parentCell.getBody();
        pnl.setLayout(new MigLayout("insets 0, gap 0"));

        pnl.add(avatarArea, "w 10%!, h 35%!");
        pnl.add(phaseIndicator, "w 5%!, h 100%!, span 1 2");
        pnl.add(scroller, "w 85%!, h 100%!, span 1 2, wrap");
        pnl.add(detailsPanel, "w 10%!, h 64%!, gapleft 1px");
    }

    public void updateManaPool() {
        detailsPanel.updateManaPool();
    }

    public void updateZones() {
        detailsPanel.updateZones();
    }

    public void updateDetails() {
        avatarArea.updateDetails();
    }

    // Accessors

    @Override
    public EDocID getDocumentID() {
        return docID;
    }

    @Override
    public DragTab getTabLabel() {
        return tab;
    }

    @Override
    public CField getLayoutControl() {
        return control;
    }

    @Override
    public void setParentCell(final DragCell cell0) {
        this.parentCell = cell0;
    }

    @Override
    public DragCell getParentCell() {
        return this.parentCell;
    }

    public PlayArea getTabletop() {
        return this.tabletop;
    }

    public JPanel getAvatarArea() {
        return this.avatarArea;
    }

    public PhaseIndicator getPhaseIndicator() {
        return phaseIndicator;
    }

    public PlayerDetailsPanel getDetailsPanel() {
        return detailsPanel;
    }

    public void setAvatar(final SkinImage avatar) {
        avatarArea.setAvatar(avatar);
    }

    /**
     * Manages display of Avatar and CounterDetails.
     */
    protected static class AvatarArea extends SkinnedPanel {
        private final VCounterDisplayPanel counters;
        private final FLabel lblAvatar = new FLabel.Builder().fontAlign(SwingConstants.CENTER)
                                                             .iconScaleFactor(1.0f)
                                                             .build();
        private final BooleanSupplier highlightIndicator;
        private final PlayerView player;

        private final Border borderAvatarSimple = new LineBorder(new Color(0, 0, 0, 0), 1);
        private final Border borderAvatarHighlighted = new LineBorder(Color.red, 2);

        public AvatarArea(PlayerView player, BooleanSupplier highlightIndicator) {
            this.highlightIndicator = highlightIndicator;
            this.player = player;
            lblAvatar.setFocusable(false);
            //lblAvatar.setText(player.getName());
            setOpaque(false);
            setBackground(FSkin.getColor(FSkin.Colors.CLR_HOVER));
            counters = new VCounterDisplayPanel(player);
            applyLayout();
            // Player area hover effect
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(final MouseEvent e) {
                    setOpaque(true);
                    if (!isHighlighted()) {
                        setBorder(new FSkin.LineSkinBorder(FSkin.getColor(FSkin.Colors.CLR_BORDERS)));
                    }
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                    setOpaque(false);
                    if (!isHighlighted()) {
                        setBorder(borderAvatarSimple);
                    }
                }
            });
        }

        protected void applyLayout() {
            setLayout(new MigLayout(
                              new LC().insets("0").gridGap("0", "0").width("100%").wrapAfter(1),
                              new AC().grow()
                      )
            );
            //setLayout(new GridLayout(2, 1, 2, 2));
            int rightPadding = 6;
            int bottomPadding = 3;
            //int spaceForCounterDetails = CounterDisplayPanel.PANEL_HEIGHT + bottomPadding;

            //add(lblAvatar, String.format("w 100%%-%spx!, h 100%%-%spx!, wrap, gap 3 3 3 0", rightPadding, spaceForCounterDetails));
            //add(counters, String.format("w 100%%!, h %spx!, wrap", CounterDisplayPanel.PANEL_HEIGHT));
            add(lblAvatar, new CC().grow().width("100%").height("100%-20px"));
            add(counters, new CC().grow().width("100%").minHeight("20px").wrap());
        }

        protected void setAvatar(final SkinImage avatar) {
            lblAvatar.setIcon(avatar);
            lblAvatar.getResizeTimer().start();
        }

        protected boolean isHighlighted() {
            return highlightIndicator.getAsBoolean();
        }

        public void updateDetails() {
            counters.updateDetails();
            final boolean highlighted = isHighlighted();
            setBorder(highlighted ? borderAvatarHighlighted : borderAvatarSimple);
            setOpaque(highlighted);
            setToolTipText(player.getDetailsHtml());
        }

    }

    public static class VCounterDisplayPanel extends JPanel {
        protected final static int PANEL_HEIGHT = 20;

        private final static int DEFAULT_VISIBLE_COUNTERS = 5; // Includes Life Total

        private final PlayerView player;

        protected List<CounterDisplay> prioritizedCounters = createPrioritizedCounterDisplays();

        protected List<CounterDisplay> createPrioritizedCounterDisplays() {
            List<CounterDisplay> cds = new LinkedList<>();
            cds.add(new LifeCounterDisplay());
            cds.add(new PoisonCounterDisplay());
            cds.add(new EnergyCounterDisplay());
            cds.add(new ExperienceCounterDisplay());
            cds.add(new RadCounterDisplay());
            cds.add(new TicketCounterDisplay());
            return cds;
        }

        /**
         * Constructor
         *
         * @param player &emsp; {@link PlayerView} providing counts for counters.
         */
        public VCounterDisplayPanel(PlayerView player) {
            this.player = player;
            setOpaque(false);
            //setBackground(FSkin.getColor(FSkin.Colors.CLR_OVERLAY));
            //setLayout(new MigLayout(
            //        new LC().insets("0").gridGap("0", "0"),
            //        new AC().grow())
            //);
            //setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
            setLayout(counterPanelLayout(1, 1))
            setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
            addFirstItem();
            setVisible(true);
        }

        protected void updateDetails() {
            refresh(DEFAULT_VISIBLE_COUNTERS);
        }

        public VCounterDisplayPanel refresh(int visibleCountersAllowed) {
            boolean hasAnythingChanged = updateCounters();
            if (hasAnythingChanged) {
                removeAll();
                addNonZeroCounters(visibleCountersAllowed);
            }
            return this;
        }

        private boolean updateCounters() {
            boolean isAnythingDifferent = false;
            for (CounterDisplay cd : prioritizedCounters) {
                boolean didCounterChange = cd.refresh(player);
                if (didCounterChange) {
                    cd.setVisible(cd::isNonZero);
                }
                isAnythingDifferent |= didCounterChange; // This becomes true only if a counter has changed.
            }
            return isAnythingDifferent;
        }

        private void addNonZeroCounters(int visibleCountersAllowed) {
            int nonZeroCounters = countNonZeroCounterDisplays();
            if (nonZeroCounters > 0) {
                int visibleCounters = Math.max(Math.min(nonZeroCounters, visibleCountersAllowed), 1); // Life is always shown
                //showInitialCounter(prioritizedCounters.getFirst(), visibleCounters);
                prioritizedCounters.stream()
                                   .filter(FLabel::isVisible)
                                   .limit(visibleCounters)
                                   .forEach(counter -> showCounter(counter, visibleCounters));
            } else {
                //add(lblLife, String.format("w 100%%!, h %spx!, wrap", PANEL_HEIGHT));
                addFirstItem();
            }
        }

        private void addFirstItem() {
            CounterDisplay firstItem = prioritizedCounters.stream()
                                                          .findFirst()
                                                          .orElse(null);
            add(firstItem, new CC().minWidth("20px"));
        }

        private int countNonZeroCounterDisplays() {
            return Math.toIntExact(prioritizedCounters.stream()
                                                      .filter(FLabel::isVisible)
                                                      .count());
        }

        private void showCounter(FLabel counter, int visibleCounters) {
            add(counter, new CC().minWidth("20px").hideMode(3));
        }

        private void showInitialCounterOld(FLabel counter, int visibleCounters) {
            add(counter, String.format("w %s%%!, h %spx!, split %s", 100 / visibleCounters, PANEL_HEIGHT, visibleCounters));
        }

        private void showCounterOld(FLabel counter, int visibleCounters) {
            add(counter, String.format("w %s%%!, h %spx!, wrap", 100 / visibleCounters, PANEL_HEIGHT));
        }

    }

    public static abstract class CounterDisplay extends FLabel {
        // Common defaults for all Counters
        private static final FLabel.Builder LABEL_TEMPLATE = new FLabel.Builder()
                .suppressFocusable(true)
                .fontAlign(SwingConstants.RIGHT)
                .fontStyle(Font.BOLD)
                .iconInBackground();

        private String name;

        protected CounterEnumType type;
        protected int count, previousCount = 0;

        public CounterDisplay(String name, FSkinProp iconProp) {
            super(LABEL_TEMPLATE.copy().iconImage(iconProp));
            setIconTextGap(25);
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
            setVerticalTextPosition(SwingConstants.CENTER);
            setHorizontalTextPosition(SwingConstants.TRAILING);
            setBorder(BorderFactory.createLineBorder(Color.green, 1));
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        /**
         * Refresh the count and update the label.
         *
         * @param player &emsp; {@link PlayerView} from which the count is derived.
         * @return boolean &emsp; <code>true</code> if the count changed since the last invocation, <code>false</code> otherwise.
         */
        public boolean refresh(PlayerView player) {
            count = retrieveCount(player);
            if (count == previousCount) return false; // No need to do anything if the count did not change.

            setText(String.valueOf(count));
            updateCriticalIndicator();
            previousCount = count;
            return true;
        }

        protected int retrieveCount(PlayerView player) {
            return player.getCounters(type);
        }

        public void setVisible(BooleanSupplier visibilityCondition) {
            setVisible(visibilityCondition.getAsBoolean());
        }

        protected void updateCriticalIndicator() {
            if (isCritical()) {
                setForeground(Color.RED);
            } else {
                FSkin.SkinColor textColor = FSkin.getColor(forge.toolbox.FSkin.Colors.CLR_TEXT);
                setForeground(textColor);
            }
        }

        protected boolean isNonZero() {
            return count > 0;
        }

        protected boolean isCritical() {
            return false;
        }
    }

    protected static class LifeCounterDisplay extends CounterDisplay {
        private final static int LIFE_CRITICAL_THRESHOLD = 5;

        public LifeCounterDisplay() {
            super("Life", FSkinProp.ICO_QUEST_LIFE);
        }

        @Override
        protected int retrieveCount(PlayerView player) {
            return player.getLife();
        }

        @Override
        protected boolean isCritical() {
            return count < LIFE_CRITICAL_THRESHOLD;
        }
    }

    protected static class PoisonCounterDisplay extends CounterDisplay {
        private final static int POISON_CRITICAL_THRESHOLD = 8;

        public PoisonCounterDisplay() {
            super("Poison", FSkinProp.IMG_POISON);
            this.type = CounterEnumType.POISON;
        }

        @Override
        protected boolean isCritical() {
            return count > POISON_CRITICAL_THRESHOLD;
        }
    }

    protected static class EnergyCounterDisplay extends CounterDisplay {
        public EnergyCounterDisplay() {
            super("Energy", FSkinProp.IMG_ENERGY);
            this.type = CounterEnumType.ENERGY;
        }
    }

    protected static class ExperienceCounterDisplay extends CounterDisplay {
        public ExperienceCounterDisplay() {
            super("Experience", FSkinProp.IMG_EXPERIENCE);
            this.type = CounterEnumType.EXPERIENCE;
        }
    }

    protected static class TicketCounterDisplay extends CounterDisplay {
        public TicketCounterDisplay() {
            super("Ticket", FSkinProp.IMG_TICKET);
            this.type = CounterEnumType.TICKET;
        }
    }

    protected static class RadCounterDisplay extends CounterDisplay {
        public RadCounterDisplay() {
            super("Rad", FSkinProp.IMG_RAD);
            this.type = CounterEnumType.RAD;
        }
    }


    private static LayoutManager topLevelLayout() {
        LC lc = new LC()
                .fill();
        if (DEBUG_RATE[0] > 0) {
            lc.debug(DEBUG_RATE[0]);
        }
        return new MigLayout(lc);
    }

    private static LayoutManager counterPanelLayout(int wrapAfter, int visibleCount) {
        int displayedCount = Math.max(Math.min(wrapAfter, visibleCount), 1);
        log.info("CounterPanelLayout displayedCount: {}", displayedCount);
        LC lc = new LC()
                .flowX()
                .wrapAfter(displayedCount);
        if (DEBUG_RATE[0] > 0) {
            lc.debug(DEBUG_RATE[0]);
        }
        return new MigLayout(lc);
        //return new FlowLayout(FlowLayout.CENTER, DEFAULT_TEXT_GAP, DEFAULT_TEXT_GAP / 2);
    }

    private static CC logoCc() {
        return new CC()
                .maxHeight(String.valueOf(AVATAR_DEFAULT_HEIGHT))
                //.minHeight(String.valueOf(AVATAR_MIN_HEIGHT))
                .width(String.valueOf(AVATAR_DEFAULT_WIDTH))
                .height(String.valueOf(AVATAR_DEFAULT_HEIGHT))
                .grow()
                ;
    }

    private static CC panelCc() {
        return new CC()
                .minHeight(String.valueOf(HEIGHT))
                .maxHeight(String.valueOf(HEIGHT))
                .minWidth(String.valueOf(COUNTER_PANEL_MIN_WIDTH))
                .width(String.valueOf(COUNTER_PANEL_DEFAULT_WIDTH))
                .height(String.valueOf(HEIGHT))
                .dockSouth()
                ;
    }

    private static CC counterCc() {
        return new CC()
                .growX()
                .push()
                .hideMode(3)
                ;
    }
}
