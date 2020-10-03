import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

public class InterfazDeUsuario extends javax.swing.JFrame {
  
  private final class ActionListenerImplementation implements java.awt.event.ActionListener {
		private final int i;
    private final int j;

		private ActionListenerImplementation(final int i, final int j) {
      this.i = i;
      this.j = j;
    }

    public void actionPerformed(final java.awt.event.ActionEvent evt) {
      //int cantPuntos=tablero.getCantPuntos(i, j, new Ficha(Figura.ROMBO, Color.ROJO));
      int cantPuntos = 0;
      if (cantPuntos > 0){
        botones[i][j].setIcon(new javax.swing.ImageIcon(
            IMAGE_PATH + Figura.ROMBO + Color.ROJO + ".png"));
        tablero.getFichas()[i][j]=new Ficha(Figura.ROMBO, Color.ROJO);
      }
    }
  }
  
  private final javax.swing.JButton[][] botones = new javax.swing.JButton[Tablero.MATRIX_SIDE][Tablero.MATRIX_SIDE];
  private final transient Tablero tablero;
  public static final String IMAGE_PATH="D:\\xllEs\\Documents\\2020 - IISem\\Análisis de Algoritmos\\Qwirkle-Game-Analysis-BackTracking-JAVA-CR-2020\\Qwirkle-Game-Analysis-BackTracking-JAVA-CR-2020\\imagenes\\";
  
  public InterfazDeUsuario(final Tablero tablero) {
    this.tablero = tablero;
    initComponents();
  }

  public void mostrarTablero() {
    java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
        for (int i = 0; i < Tablero.MATRIX_SIDE; i++)
          for (int j = 0; j < Tablero.MATRIX_SIDE; j++)
            if (tablero.getFichas()[i][j] != null)
              botones[i][j].setIcon(new javax.swing.ImageIcon(
                InterfazDeUsuario.IMAGE_PATH + tablero.getFichas()[i][j].figura + tablero.getFichas()[i][j].color + ".png"));
			}
		  });
  }
  
  public void mostrarJugada(Jugada jugada) {
    for(Jugadita par:jugada.jugaditas)
        botones[par.x][par.y].setIcon(new javax.swing.ImageIcon(
          IMAGE_PATH + par.ficha.figura + par.ficha.color + ".png"));
  }
  
  //todo: Que el usuario pueda jugar las fichas de su mano donde el señor Tablero le diga 

  private void initComponents() {

    for (int i = 0; i < Tablero.MATRIX_SIDE; i++) {
      for (int j = 0; j < Tablero.MATRIX_SIDE; j++) {
        botones[i][j] = new javax.swing.JButton();
        botones[i][j].addActionListener(new ActionListenerImplementation(i, j));
      }
    }

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    SequentialGroup sequentialGroup1, sequentialGroup2;
    ParallelGroup parallelGroup1, parallelGroup2;
    parallelGroup1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
    sequentialGroup1 = layout.createSequentialGroup().addGap(22, 22, 22);
    parallelGroup2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
    for (int i = Tablero.MATRIX_SIDE - 1; i >= 0; i--) {
      sequentialGroup2 = layout.createSequentialGroup();
      for (int j = 0; j < Tablero.MATRIX_SIDE; j++) {
        sequentialGroup2
            .addComponent(botones[i][j], javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
      }
      parallelGroup2.addGroup(sequentialGroup2);
    }
    layout.setHorizontalGroup(
        parallelGroup1.addGroup(sequentialGroup1.addGroup(parallelGroup2).addContainerGap(94, Short.MAX_VALUE)));
    parallelGroup1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
    sequentialGroup1 = layout.createSequentialGroup().addContainerGap(56, Short.MAX_VALUE);
    for (int i = 0; i < Tablero.MATRIX_SIDE; i++) {
      parallelGroup2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
      for (int j = Tablero.MATRIX_SIDE - 1; j >= 0; j--) {
        parallelGroup2.addComponent(botones[i][j], javax.swing.GroupLayout.PREFERRED_SIZE, 40,
            javax.swing.GroupLayout.PREFERRED_SIZE);
      }
      sequentialGroup1.addGroup(parallelGroup2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
    }
    parallelGroup1.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sequentialGroup1.addGap(61, 61, 61));
    layout.setVerticalGroup(parallelGroup1);
    pack();
  }
}