   M S B A M L   `   `   `  ����J  FReadCardActiveX, Version=1.3.1.0, Culture=neutral, PublicKeyToken=nullB2clr-namespace:GSFramework;assembly=ReadCardActiveXGSFramework  5   �   [ WSystem.Windows.Forms, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089Y@clr-namespace:System.Windows.Forms;assembly=System.Windows.FormsSystem.Windows.Forms 6    SelfHelpSystem   SelfHelpSystem.QPPage2_1   6   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35^ ZWindowsFormsIntegration, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     6g   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     6�   Crcax2clr-namespace:GSFramework;assembly=ReadCardActiveX     6�   Rforms@clr-namespace:System.Windows.Forms;assembly=System.Windows.Forms     6   -   5          N� Title$  GPPage��5   *    � 	Resources 5       �� MergedDictionaries �� Source�� 5       5      �� 5   
   $ /styles.xaml?�6   %    5       N� 
Background 5
      �� 5       �� ImageSource$ /pic/bg1.png��6   .��5      �� $��0,0,0,0q�6    �� Orientation$ Vertical=�6    $��Center=�67   .+�5      �� -   5        $��dockfull��5      $��	0,155,0,0q�6!   $
��1015��64   $
��1090��6A      stackPanelFull#��[  6O   $ Vertical=�6w   $��Center=�6�   ��5      �� 5   
   $ /pic/bgdpfull.png��6   .+�5      �� $ Vertical=�6   $	��Top=�6*   $
��1015��6B   .+�5   
   �� $��取票信息-刷身份证��6   $��20,20,20,20q�6%     commTextBlock#��[ 6:   �� 5   
   ���	#FFFFFFFF$��1q�6)   $��4��6=   ���	#FFFFFFFF$��
10,5,10,10q�6_   5   	   �� 5      $ Vertical=�6   $	��Top=�6*   $
��1015��6B   $	��740��6O   .+�5   
   �� $ 
Horizontal=�6   $��Center=�6.   $	��620��6K   $��50q�6X   $	��915��6d   ��5      �� 5      $ /pic/b2.png��6   .+�5      �� $	��820��6   $��50q�6#   $
��Left=�6/   $ 
Horizontal=�6J   ��5      �� 5      $ /pic/bw.png��6   .+�5      �� $��
/pic/6.png��6   $	��500��6(   $	��450��65   �� 5       ���	#FF858686$��1q�6-   $	��600��6A   $��2��6N   ���	#FF858686$��	0,30,0,30q�6o   �� 5!      $ Vertical=�6   $��Center=�60   $	��365��6K   .+�5$      7  1System.Windows.Forms.Integration.WindowsFormsHost  $��0��6/   $��0��69      Child. 5%   &   !   GSFramework.ReadCardActiveX  -   5        readcard��5%   ;   5'      �� 5)      $��请刷身份证��6(   $��45(�65   ���	#FF464646 �� TextAlignment$ Center=�6n   5*      5+      5,      5-   	   �� 5.      $ Vertical=�6   $	��Top=�6*   $
��1015��6B   $	��220��6O   .+�5/   
   �� $ 
Horizontal=�6   $��Center=�6.   $	��220��6I   .+�50      �� -   5        $��btn_SY��50      $��首  页��6!   $��20,0,0,0q�60   $	��100��6B   $��50(�6O   $	��280��6]     CommButton11#��[ 6i   6�   �� 51      -   5        $��btn_FH��51      $��返  回��6!   $��	400,0,0,0q�60   $y�Visible=�6C   $	��100��6X   $��50(�6e   $	��280��6s     
CommButton#��[ 6   6�   52      53   	   54      55      56      