   M S B A M L   `   `   `  ����  SelfHelpSystem    SelfHelpSystem.MainWindow   5   	   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35^ ZWindowsFormsIntegration, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     6  R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     6W  -   5          � Title$  自助售票机系统��5   -   $	��900��6=   $
��1600��6J    � WindowStartupLocation$ Manual=�6W    � WindowState$ 	Maximized=�6v   ���	#00FFFFFF � AllowsTransparency .  � WindowStyle$
 None=�6�   .��5      �� $
��Auto=�6   $
��Auto=�6A   ���	#00FFFFFF.��5      �� -   5        $��
mainCanvas��5      $
��1080��6   $
��1920��6,   .+�5      �� -   5        $��	lblSecond��5      $��50(�6    �� Left$	 475��6-   
 �� Top$
 1735��6?    K� ZIndex$
 1000��6Q   $��Center=�6e   $	��150��6�   � 5      -   5        $��mainGrid��5      $
��1080��6   $
��1920��6*    � RowDefinitions 5   
   �� 5      -   5        	 �Name$
	 row1��5      $	��608 �6&   �� 5	      -   5        $
	 row2��5	      $	��662 �6&   �� 5
      -   5        $
	 row3��5
      $	��650 �6&   
 � ColumnDefinitions
 5   
   �� 5      .+�5   
   � -   5        $��TopPanel��5      $��0��6$    � Orientation$ Vertical=�61   .+�5       � -	   5        $��topFrame��5        � NavigationUIVisibility$ Hidden=�6"   5      � 5   
   -
   5        $��MiddlePanel��5      $��1��6'   $ Vertical=�64   .+�5       � -   5        $��middleFrame��5      $ Hidden=�6%   5      ]� 5      -   5        $��BottomPanel��5      $��2��6/   .+�5       � -   5        $��	mainFrame��5      $ Hidden=�6-   $��0,0,0,0q�6M   5      5   	   5      5      5      