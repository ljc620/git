   M S B A M L   `   `   `  ����  SelfHelpSystem    SelfHelpSystem.QPPage3   5      \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35^ ZWindowsFormsIntegration, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     6L   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     6�   -   5          N� Title$  GPPage��5   (    � 	Resources 5       �� MergedDictionaries �� Source�� 5       5      �� 5   
   $ /styles.xaml?�6   %    5       N� 
Background 5
      �� 5       �� ImageSource$ /pic/bg1.png��6   .��5      �� $��0,0,0,0q�6    �� Orientation$ Vertical=�6    $��Center=�67   .+�5      �� -   5        $��dockfull��5      $��	0,155,0,0q�6!   $
��1015��64   $
��1090��6A      stackPanelFull#��[  6O   $ Vertical=�6w   $��Center=�6�   ��5      �� 5   
   $ /pic/bgdpfull.png��6   .+�5      �� $ Vertical=�6   $	��Top=�6*   $
��1015��6B   .+�5   
   �� $��取票信息-订单信息��6   $��20,20,20,20q�6%     commTextBlock#��[ 6:   �� 5   
   ���	#FFFFFFFF$��1q�6)   $��4��6=   ���	#FFFFFFFF$��
10,5,10,10q�6_   5   	   �� 5      $ 
Horizontal=�6   $��Center=�6,   $
��1015��6I   $	��740��6V   $��0q�6c   .+�5   
   �� $ Vertical=�6   $��Center=�6,   $	��640��6G   $	��915��6T   $��	50,0,50,0q�6`   ��5      �� 5      $ /pic/b2.png��6   .+�5      �� -   5        $��userName��5      $��50��6&   $��
30,30,30,0q�62   $��40(�6F   $��用户信息：��6^   ���	#FFFFFFFF$
��Left=�6�   &   System.Windows.Controls.DataGrid  5      -   5        $��	dataGrid1��5      $��Center=�6&   $��30q�6C   $	��Top=�6O   $	��350��6g   $	��840��6t   $��25(�6�      AutoGenerateColumns .  !   EnableColumnVirtualization .  	   
IsReadOnly	 . ��6�   �� 
   RowStyle
 5      �� 5       �� 
TargetType)  #System.Windows.Controls.DataGridRow"  6   .�5      ��  �� Property � �� �� Value$ 70��6<   �� 5        � ��$ 30(�65   5!         ColumnHeaderStyle 5#      �� 5$      =  7System.Windows.Controls.Primitives.DataGridColumnHeader"  6   .�5%      ��  � �� �	#FF1E90FF67   �� 5&       � �� �	#FFFFFFFF67   �� 5'       � ��$ 70��6<   �� 5(       � ��$ Center=�6G   5)         Columns 5+      0  *System.Windows.Controls.DataGridTextColumn  5,      ,  &System.Windows.Controls.DataGridColumn   Width5  /System.Windows.Controls.DataGridLengthConverter$	 35* 6#      Header$ 景区名称��6/   1  +System.Windows.Controls.DataGridBoundColumn   Binding 6=   �� *
tickettype+  5-      $	 25* 6#   $ 购买数量��6/    6=   �� *ticketcount+  5.      $	 40* 6#   $ 门票日期��6/    6=   �� *
ticketdate+4  .System.Windows.Controls.DataGridTemplateColumn  5/      $ 0* 6'      
Visibility$ Hidden=�62      HeaderTemplate 50      �� 51      .R�52      �� $��CheckAll��6   $��全选��6/   $��25(�6<   ���	#FFFFFFFF$��Center=�6a   $y�Hidden=�6�   53      55      57      �� 58      -   5        $��btn_GQ��58      $��
520,0,0,40q�6!   $��确  定��65   $	��110��6D   $	��300��6Q     
CommButton#��[ 6]   6�   59      5:   	   �� 5;      $ Vertical=�6   $	��Top=�6*   $
��1015��6B   $	��220��6O   .+�5<   
   �� $ 
Horizontal=�6   $��Center=�6.   $	��220��6I   .+�5=      �� -   5        $��btn_SY��5=      $��首  页��6!   $��20,0,0,0q�60   $	��100��6B   $��50(�6O   $	��280��6]     CommButton11#��[ 6i   6�   �� 5>      -   5        $��btn_FH��5>      $��返  回��6!   $��	400,0,0,0q�60   $y�Visible=�6C   $	��100��6X   $��50(�6e   $	��280��6s   #��[ 6   6�   5?      5@   	   5A      5B      5C      