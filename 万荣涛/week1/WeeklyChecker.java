import lombok.val;

import java.io.File;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WeeklyChecker
{
    public static void main(String[] args)
    {
        val projectPath = "E:\\ACodeSpace\\Stream-weekly-report-24";
        val projectFile = new File(projectPath);
        val projectFiles = Arrays.stream(Objects.requireNonNull(projectFile.listFiles()));
        List<File> allStudents = projectFiles.filter(File::isDirectory)
                                             .filter(f -> !f.getName().startsWith("."))
                                             .filter(f -> !f.getName().contains("刁一轩"))
                                             .toList();

        StringJoiner qualifiedStudents = new StringJoiner("、");
        List<UnqualifiedStudent> unqualifiedStudents = new ArrayList<>();

        for(File student : allStudents)
        {
            try
            {
                checkStudent(student);
                qualifiedStudents.add(student.getName());
            }
            catch(WeekNotFoundException | WeekNotQualifiedException e)
            {
                unqualifiedStudents.add(new UnqualifiedStudent(student.getName(), e.getMessage()));
            }
        }

        System.out.println("合格学生：" + qualifiedStudents);
        String unqualifiedMessages = unqualifiedStudents.stream().map(UnqualifiedStudent::toString).collect(
                Collectors.joining("\n", "\n", ""));
        System.out.println("不合格学生：" + unqualifiedMessages);
    }

    private static void checkStudent(File student) throws WeekNotFoundException, WeekNotQualifiedException
    {
        val nowWeekName = "week1";
        val weeks = Arrays.stream(Objects.requireNonNull(student.listFiles()));
        final Optional<File> nowWeek = weeks.filter(f -> f.getName().compareToIgnoreCase(nowWeekName) == 0).findFirst();

        if(nowWeek.isEmpty())
        {
            throw new WeekNotFoundException(MessageFormat.format("草，{0}连{1}文件夹都还没有创建",
                                                                 student.getName(),
                                                                 nowWeekName));
        }

        checkWeek(student.getName(), Arrays.asList(Objects.requireNonNull(nowWeek.get().listFiles())));
    }

    private static void checkWeek(String studentName, List<File> week) throws WeekNotQualifiedException
    {
        val messageList = new ArrayList<String>();

        if(week.stream().flatMap(WeeklyChecker::expandRecursively).noneMatch(f -> f.getName().contains("周报")))
        {
            messageList.add(studentName + "周报未完成");
        }

        if(week.stream().flatMap(WeeklyChecker::expandRecursively).noneMatch(f -> f.getName().contains(".html")))
        {
            messageList.add(studentName + "前端html学习未完成");
        }

        if(!messageList.isEmpty())
        {
            throw new WeekNotQualifiedException(String.join("，", messageList));
        }
    }

    private static Stream<File> expandRecursively(File file)
    {
        if(file.isFile())
        {
            return Stream.of(file);
        }
        else
        {
            File[] subFiles = file.listFiles();
            if(subFiles == null)
            {
                return Stream.empty();
            }
            return Arrays.stream(subFiles).flatMap(WeeklyChecker::expandRecursively);
        }
    }
}

class WeekNotFoundException extends Exception
{
    public WeekNotFoundException(String message)
    {
        super(message);
    }
}

class WeekNotQualifiedException extends Exception
{
    public WeekNotQualifiedException(String message)
    {
        super(message);
    }
}

record UnqualifiedStudent(String studentName, String message)
{
    @Override
    public String toString()
    {
        return studentName + "：" + message;
    }
}