   M S B A M L   `   `   `  ����   SelfHelpSystem    SelfHelpSystem.QPPage3   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35^ ZWindowsFormsIntegration, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     -     N� Title$  GPPage�� � 	Resources  �� MergedDictionaries �� Source��  �� $ /styles.xaml?�%     N� 
Background ��  �� ImageSource$ /pic/bg1.png��.���� $��0,0,0,0q� �� Orientation$ Vertical=�$��Center=�.+��� -   $��dockfull��$��	0,155,0,0q�$
��1015��$
��1090��   stackPanelFull#��[  $ Vertical=�$��Center=����� $ /pic/bgdpfull.png��.+��� $ Vertical=�$	��Top=�$
��1015��.+��� $��取票信息-订单信息��$��20,20,20,20q�  commTextBlock#��[ �� ���	#FFFFFFFF$��1q�$��4�����	#FFFFFFFF$��
10,5,10,10q��� $ 
Horizontal=�$��Center=�$
��1015��$	��740��$��0q�.+��� $ Vertical=�$��Center=�$	��640��$	��915��$��	50,0,50,0q����� $ /pic/b2.png��.+��� -   $��userName��$��50��$��
30,30,30,0q�$��40(�$��用户信息：�����	#FFFFFFFF$
��Left=�&   System.Windows.Controls.DataGrid  -   $��	dataGrid1��$��Center=�$��30q�$	��Top=�$	��350��$	��840��$��25(�   AutoGenerateColumns .  !   EnableColumnVirtualization .  	   
IsReadOnly	 . ���� 
   RowStyle
 ��  �� 
TargetType)  #System.Windows.Controls.DataGridRow"  .���  �� Property � �� �� Value$ 70����  � ��$ 30(�   ColumnHeaderStyle �� =  7System.Windows.Controls.Primitives.DataGridColumnHeader"  .���  � �� �	#FF1E90FF��  � �� �	#FFFFFFFF��  � ��$ 70����  � ��$ Center=�   Columns 0  *System.Windows.Controls.DataGridTextColumn  ,  &System.Windows.Controls.DataGridColumn   Width5  /System.Windows.Controls.DataGridLengthConverter$	 35*    Header$ 景区名称��1  +System.Windows.Controls.DataGridBoundColumn   Binding �� *
tickettype+  $	 25* $ 购买数量�� �� *ticketcount+  $	 40* $ 门票日期�� �� *
ticketdate+4  .System.Windows.Controls.DataGridTemplateColumn  $ 0*    
Visibility$ Hidden=�   HeaderTemplate �� .R��� $��CheckAll��$��全选��$��25(����	#FFFFFFFF$��Center=�$y�Hidden=��� -   $��btn_GQ��$��
520,0,0,40q�$��确  定��$	��110��$	��300��  
CommButton#��[ �� $ Vertical=�$	��Top=�$
��1015��$	��220��.+��� $ 
Horizontal=�$��Center=�$	��220��.+��� -   $��btn_SY��$��首  页��$��20,0,0,0q�$	��100��$��50(�$	��280��  CommButton11#��[ �� -   $��btn_FH��$��返  回��$��	400,0,0,0q�$y�Visible=�$	��100��$��50(�$	��280��#��[ 