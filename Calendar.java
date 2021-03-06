package project4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class Calendar extends Application
{
   private BorderPane containerPane = new BorderPane();
   private LocalDateTime date = LocalDateTime.now();
   private String appointmentFile = "src/project4/appointmentFile.csv";
   private File apps = new File(appointmentFile);
   private Scene scene;
   private Scene appointmentScene;
   private String[] days = {"S","M","T","W","T","F","S"};
   private String[] months = {"January", "Feburary", "March","April","May","June","July","August","September","October","November","December"};
   private int curMonth = date.getMonthValue();
   private int curYear = date.getYear();
   private int curDay =1;
   private Text txtcurMonth = new Text(date.getMonth()+"");
   private Text txtcurYear = new Text(date.getYear()+"");
   private VBox appointmentPane = new VBox(20);
   private int curRow=0;
   private int curCol=0;
   //Use this stage if you decide to complete the extra credit
   
   private Stage appointmentStage = new Stage();
   

   @Override
   public void start(Stage primaryStage)
   {
      scene = new Scene(containerPane, 1000, 800);
      containerPane.setStyle("-fx-background-color: whitesmoke;");
      setupTopPane();
      GridPane gp = setupMonthPane(date.getYear(), date.getMonthValue());
      containerPane.setCenter(gp);
        
      primaryStage.setTitle("Calendar");
      primaryStage.setMinWidth(1000);
      primaryStage.setMinHeight(800);
      primaryStage.setScene(scene);
      primaryStage.show();
        
        
      //Use the following if you decide to complete the extra credit
      
      appointmentScene = new Scene(appointmentPane, 350, 250);
      setupAppointmentPane();
      appointmentStage.setTitle("Add Event");
      appointmentStage.setScene(appointmentScene);
      
   }
    
   public void setupTopPane()
   {
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
       
       HBox topH = new HBox(10);
       
       
       HBox filler = new HBox();
       filler.setHgrow(filler,Priority.ALWAYS);
       txtcurMonth.setFont(Font.font(20));
       txtcurYear.setFont(Font.font(20));
       
       Button leftNav = new Button("<");
       Button rightNav = new Button(">");
       Button yearNav = new Button("Year");
       Button todayNav = new Button("Today");
       
     
       leftNav.setOnMouseClicked(e ->{
           
           if(curMonth > 1)
           {
               
               curMonth--;
               setTopText();
               topH.getChildren().clear();
               topH.getChildren().addAll(txtcurMonth,txtcurYear,filler,leftNav,yearNav,todayNav,rightNav);
               displayAppointments(setupMonthPane(curYear,curMonth));
            
           }
           else{
               
               curMonth = 12;
               curYear -= 1; 
               setTopText();
               topH.getChildren().clear();
               topH.getChildren().addAll(txtcurMonth,txtcurYear,filler,leftNav,yearNav,todayNav,rightNav);
               displayAppointments(setupMonthPane(curYear,curMonth));
             
           }
      });
        rightNav.setOnMouseClicked(e->{
             if(curMonth < 12)
             {
                 curMonth++;
                 setTopText();
                 topH.getChildren().clear();
                 topH.getChildren().addAll(txtcurMonth,txtcurYear,filler,leftNav,yearNav,todayNav,rightNav);
                 displayAppointments(setupMonthPane(curYear,curMonth));
             
             }
             else{
                 curMonth =1;
                 curYear++;
                 setTopText();
                 topH.getChildren().clear();
                 topH.getChildren().addAll(txtcurMonth,txtcurYear,filler,leftNav,yearNav,todayNav,rightNav);
                 displayAppointments(setupMonthPane(curYear,curMonth));
                
             }
             
         });
        yearNav.setOnMouseClicked(e ->{
          
            containerPane.setCenter(twelveMonthsPane());
            curMonth = date.getMonthValue();
            curYear = date.getYear();
            setTopText();
            topH.getChildren().clear();
            topH.getChildren().addAll(txtcurYear,filler,leftNav,yearNav,todayNav,rightNav);
        });
        todayNav.setOnMouseClicked(e->{
         
        
           curMonth = date.getMonthValue();
           curYear = date.getYear();
           setTopText();
           topH.getChildren().clear();
           topH.getChildren().addAll(txtcurMonth,txtcurYear,filler,leftNav,yearNav,todayNav,rightNav);
           displayAppointments(setupMonthPane(curYear,curMonth));
        });
       
       topH.getChildren().addAll(txtcurMonth,txtcurYear,filler,leftNav,yearNav,todayNav,rightNav);
       topH.setAlignment(Pos.BOTTOM_CENTER);
       containerPane.setMargin(topH,new Insets(15,15,0,15));
       containerPane.setTop(topH);
               
   
   
   
   }
   public void setTopText()
   {
        LocalDate localDate = LocalDate.of(curYear, curMonth, 1);
        txtcurMonth.setText(localDate.getMonth().name()+"");
        txtcurYear.setText(localDate.getYear()+"");
   }
   public GridPane setupMonthPane(int yearValue, int monthValue)
   {
      GridPane monthPane = new GridPane();
      containerPane.setMargin(monthPane,new Insets(20));
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
         int numCols = 7 ;
         int numRows = 7 ;
        for (int i = 0; i < numCols; i++) {
            
            if(i == 0 )
            {
                RowConstraints stRow = new RowConstraints();
                stRow.setPercentHeight(50.0 / numCols);
                monthPane.getRowConstraints().add(stRow);
                
                ColumnConstraints stCol = new ColumnConstraints();
                stCol.setPercentWidth(100.0 / numCols);
                monthPane.getColumnConstraints().add(stCol);
                        
            }
            else{
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            monthPane.getColumnConstraints().add(colConst);
            
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            monthPane.getRowConstraints().add(rowConst);
            }
        }
        
        for(int i = 0 ; i < numRows; i++)
        {
           Text day = new Text(days[i]+"");
           StackPane s = new StackPane();
           s.getChildren().add(day);
           monthPane.add(s,i,0);
        }
       
        monthPane.setGridLinesVisible(true);
        fillUpMonth(monthPane, yearValue, monthValue);
         
      
        return monthPane;
   }

   public void fillUpMonth(GridPane monthGP, int yearValue, int monthValue)
   {
        //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
      int curDate = date.getDayOfMonth();
      LocalDate localDate = LocalDate.of(yearValue, monthValue, 01);
      int dayIntValue = localDate.getDayOfWeek().getValue();
   
     if(dayIntValue == 7)
     {
         dayIntValue = 0;
     }
      int daysInMonth = localDate.lengthOfMonth();
      int start = 1;
     
      int lengthOfPreviousMonth;
    if(monthValue > 1)
    {    
       // YearMonth previousMonth = YearMonth.of(yearValue, monthValue -1);
        lengthOfPreviousMonth = LocalDate.of(yearValue,monthValue -1,1).lengthOfMonth();
    }
    else
    {
       // YearMonth previousMonth = YearMonth.of(yearValue -1, 12);
        lengthOfPreviousMonth = LocalDate.of(yearValue -1, monthValue, 1).lengthOfMonth();
    }
        

   int prevMonthStart = 1 + lengthOfPreviousMonth - dayIntValue;

   
    int end = 1;
    
     for(int row =1  ; row < 7 ; row++)
     {
        for(int col = 0;  col < 7 ; col++)
        {
            
            VBox vb = new VBox();
            StackPane s = new StackPane();
            
            if(prevMonthStart <= lengthOfPreviousMonth)
            {
                Text num = new Text(prevMonthStart+"");
                num.setFill(Color.GREY);
                prevMonthStart++;
                s.getChildren().add(num);
                vb.getChildren().add(s);
                vb.setAlignment(Pos.CENTER);
                monthGP.add(vb,col,row);
                
            }
            else if(start <= daysInMonth){
                if(start == curDate && date.getMonthValue() == monthValue && date.getYear() == yearValue)
                {
                    Circle cir = new Circle(s.getLayoutX(),s.getLayoutY(),8);
                    cir.setFill(Color.RED);
                    Text cur = new Text(start +"");
                    cur.setFill(Color.WHITE);
                   
                    cur.setOnMouseClicked(e->{
                        
                        
                        curDay = Integer.parseInt(cur.getText());
                       
                        clear();
                        appointmentStage.show();
                       
                    });
                    s.getChildren().addAll(cir,cur);
                    vb.getChildren().add(s);
                    vb.setAlignment(Pos.CENTER);
                    monthGP.add(vb,col,row);
                    start++;
                }
                else{
                    Text weekDay = new Text(start +"");
               
                    weekDay.setOnMouseClicked(e->{
                        
                        
                        curDay = Integer.parseInt(weekDay.getText());
                        clear();
                        appointmentStage.show();
                         
                    });
                    s.getChildren().add(weekDay);
                    vb.getChildren().add(s);
                    vb.setAlignment(Pos.CENTER);
                    monthGP.add(vb,col,row);
                    start++;
                }
            }
            else{
                Text txt = new Text(end +"");
                txt.setFill(Color.GREY);
                s.getChildren().add(txt);
                monthGP.add(s,col,row);
                end++;
                
            }
        }
    }
        
        
   }
  
   
   public GridPane twelveMonthsPane()
   {
      
      GridPane twelve = new GridPane();
      twelve.setAlignment(Pos.CENTER);
      twelve.setHgap(20);
      BorderPane.setMargin(twelve,new Insets(10,0,0,0));
   
      int monthCounter = 0;
       for(int row = 0 ; row<3 ;row++)
       {
                RowConstraints rowConst = new RowConstraints();
                rowConst.setPercentHeight(100.0 / 3);
                twelve.getRowConstraints().add(rowConst);
                
                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPercentWidth(100.0 / 4);
                twelve.getColumnConstraints().add(colConst);
               
           for(int col = 0; col< 4; col++)
           {
                
               VBox vb1 = new VBox();
               Text txtMonth = new Text(months[monthCounter]);
               txtMonth.setTextAlignment(TextAlignment.CENTER);
            
               GridPane gPane = setupMonthPane(2019,monthCounter+1);
               monthCounter++;
             
               gPane.setGridLinesVisible(false);
               gPane.setHgap(20);
                
               vb1.getChildren().addAll(txtMonth,gPane);
               vb1.setAlignment(Pos.CENTER);
                
               twelve.setMargin(vb1,new Insets(0,10,0,0));
               twelve.add(vb1, col, row);
               
            
       
           }
       
            
       }
        
        
        
      return twelve;
   }
   
   
   
   //The following is for the extra credit
    
   public void setupAppointmentPane()
   {
        //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
        LocalDateTime newDate = LocalDateTime.now();
        Label lblTitle = new Label("Title:");
        TextField txtField = new TextField("");
        
        Label lblTime = new Label("Time:");
        ComboBox<String> cbHour = new ComboBox<>();
        ComboBox<String> cbMin = new ComboBox<>();
        
        for(int i = 0; i < 24; i++)
        {
            
            String leadingZero = String.format ("%02d", i);
            cbHour.getItems().add((leadingZero));
        }
      cbHour.setValue(String.format("%02d", newDate.getHour()));      
        for(int i = 0; i < 60; i++)
        {
            
            String leadingZero = String.format("%02d",i);
            cbMin.getItems().add(leadingZero);
            
        }
         cbMin.setValue(String.format("%02d", newDate.getMinute()));
        
        Button btnClear = new Button("Clear");
        Button btnSubmit = new Button("Submit");
        
        HBox hbTitle = new HBox(10);
        hbTitle.getChildren().clear();
        hbTitle.getChildren().addAll(lblTitle,txtField);
        hbTitle.setAlignment(Pos.CENTER_LEFT);
        hbTitle.setMargin(lblTitle,new Insets(0,0,0,70));
        hbTitle.setMargin(txtField,new Insets(0,0,0,25));
       
        HBox hbTime = new HBox(10);
        hbTime.getChildren().clear();
        hbTime.getChildren().addAll(lblTime,cbHour,cbMin);
        hbTime.setAlignment(Pos.CENTER_LEFT);
        hbTime.setMargin(lblTime,new Insets(0,0,0,70));
        hbTime.setMargin(cbHour,new Insets(0,0,0,21));
        
        
        HBox hbButtons = new HBox(20);
        hbButtons.getChildren().clear();
        hbButtons.getChildren().addAll(btnClear,btnSubmit);
        hbButtons.setAlignment(Pos.CENTER);
        hbButtons.setMargin(btnClear,new Insets(20,0,0,50));
        hbButtons.setMargin(btnSubmit,new Insets(20,0,0,10));
   
        btnClear.setOnMouseClicked(e->{
            clear();
            
        
        });
        
    
        btnSubmit.setOnMouseClicked(e->{
            
            storeAppointment(txtField,cbHour,cbMin);
            appointmentStage.close();
            displayAppointments(setupMonthPane(curYear,curMonth));
            
        });
        appointmentPane.getChildren().addAll(hbTitle,hbTime,hbButtons);
        appointmentPane.setAlignment(Pos.CENTER);
        
         displayAppointments(setupMonthPane(curYear,curMonth));
        
   }
 
   public void displayAppointments(GridPane monthPane)
   {
       containerPane.setCenter(monthPane);
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
       try{
           Scanner read = new Scanner(apps);
           while(read.hasNext())
           {
               String line = read.nextLine();
               String[] s = line.split(",");
               if(Integer.parseInt(s[1]) == curYear && Integer.parseInt(s[2]) == curMonth)
               {
                   List list = monthPane.getChildren();
                   Iterator<Node> iterator = list.listIterator();
                   while(iterator.hasNext())
                   {
                       Node node = iterator.next();
                       if(node instanceof VBox)
                       {
                           List vBoxList = ((VBox)node).getChildren();
                           Iterator<Node> vBoxIterator = vBoxList.listIterator();
                           while(vBoxIterator.hasNext())
                           {
                               Node vBoxNode = vBoxIterator.next();
                               if(vBoxNode instanceof StackPane)
                               {
                                   StackPane sta = (StackPane)vBoxNode;
                                   for(Node spNode: sta.getChildren())
                                   {
                                       if(spNode instanceof Text)
                                       {
                                           Text txt = (Text) spNode;
                                           int temp = Integer.parseInt(txt.getText());
                                           if(temp == Integer.parseInt(s[3]) && txt.getFill() == Color.BLACK || temp == Integer.parseInt(s[3]) && txt.getFill() == Color.WHITE)
                                           {
                                               Label lbl = new Label(s[4]+":" + s[5]+" " + s[0]);
                                               lbl.setStyle("-fx-text-fill:green");
                                               lbl.setWrapText(true);
                                               lbl.setTextAlignment(TextAlignment.CENTER);
                                               ((ListIterator<Node>) vBoxIterator).add(lbl);
                                           }
                                       }
                                   }
                               }
                           }   
                       }
                   }
               
               }
           
           }
       }
       catch(FileNotFoundException ex)
       {
           System.out.println("File not Found");
       }


   }

   public void clear()
   {
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
     appointmentPane.getChildren().clear();
     setupAppointmentPane();
      
      
   }

    
   public void storeAppointment(TextField txt, ComboBox hour, ComboBox min)
   {   
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
     try{
           
        
      
        FileWriter writerObject = new FileWriter(apps,true);
        
        writerObject.write(txt.getText()+",");
        writerObject.write(curYear +",");
        writerObject.write(curMonth +",");
        writerObject.write(curDay +",");
        writerObject.write(hour.getValue()+",");
        writerObject.write(min.getValue()+",");
        writerObject.write("\n");
        
        writerObject.close();
        
       }
     catch(IOException ex)
     {
         System.out.println("File not Found");
     }
   }
   /**
    * @param args the command line arguments
    */
   public static void main(String[] args)
   {
      launch(args);
   }
}

