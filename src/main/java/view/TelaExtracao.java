package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.GerenciadorExtracao;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class TelaExtracao extends JFrame {

	private JPanel contentPane;
	private JLabel lblStatus;
	private JButton btnParar;
	private JButton btnIniciar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaExtracao frame = new TelaExtracao();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaExtracao() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 537, 388);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JSeparator separator = new JSeparator();

		lblStatus = new JLabel("Status do Processamento: ");
		lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 13));

		btnIniciar = new JButton("    Iniciar Extração");
		btnIniciar.setIcon(new ImageIcon(TelaExtracao.class.getResource("/images/build.png")));
		btnIniciar.addActionListener(new ActionListener() {

			GerenciadorExtracao gerenciador;
			public void actionPerformed(ActionEvent arg0) {
				try {

					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
						protected Void doInBackground() throws Exception {

							gerenciador = new GerenciadorExtracao(lblStatus);
							gerenciador.extrairPlugins();
							gerenciador.extrairTemas();
							gerenciador.extrairScripts();
							return null;
						};
					};
					worker.execute();

					btnParar.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							gerenciador.setCancela(true);
						}
					});

				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}

		});

		btnParar = new JButton("     Parar Automação");
		btnParar.setIcon(new ImageIcon(TelaExtracao.class.getResource("/images/cancel16.png")));

		JSeparator separator_1 = new JSeparator();

		JLabel lblNewLabel = new JLabel("Ares Soluções em TI");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblStatus, GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(45)
										.addComponent(btnIniciar, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
										.addComponent(btnParar, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
										.addGap(4)))
						.addGap(27))
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
						.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
						.addContainerGap())
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
						.addGap(13)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblStatus, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addGap(8)
						.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(41)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnIniciar, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
								.addComponent(btnParar, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
						.addGap(26))
				);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}

}
