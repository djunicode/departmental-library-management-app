from celery import shared_task
from .models import Issue, WaitingList, Copy, Notification, Student
import datetime


@shared_task
def check_fine():
    issues = Issue.objects.filter(
        return_date__isnull=True, teacher__isnull=True, paid=False
    ).exclude(issue_date__isnull=True)
    for issue in issues:
        temp = datetime.date.today() - issue.issue_date
        if temp.days > 7:
            issue.fine = 10 * (temp.days - 7)
            issue.save(update_fields=["fine"])
            usr = Student.objects.get(email=issue.student.email)
            Notification.objects.create(
                nf_type="FINE",
                user=usr,
                notification="Your total fine for the book "
                + issue.copy.book.name
                + " is "
                + str(issue.fine),
            )
    items = WaitingList.objects.filter(is_alerted=True)
    for item in items:
        temp = datetime.date.today() - item.alerted_on.date()
        copies = Copy.objects.filter(book=item.book, is_available=False)
        if item.student and temp.days > 2:
            Issue.objects.filter(copy__in=copies, student=item.student).delete()
            item.delete()
            Notification.objects.create(
                nf_type="REMOVED",
                user=item.student,
                notification="You are removed from the waiting list for not collecting the book "
                + item.book.name
                + " for 2 days",
            )
        elif item.teacher and temp.days > 5:
            Issue.objects.filter(copy__in=copies, teacher=item.teacher).delete()
            item.delete()
            Notification.objects.create(
                nf_type="REMOVED",
                user=item.teacher,
                notification="You are removed from the waiting list for not collecting the book "
                + item.book.name
                + " for 5 days",
            )
