# Task

## Story
There is a company that is using a self written workflow management system for collaboration both
internally and with its contractors. That system has a rough-and-ready export job that writes out the
internal state every night into the following files using an arbitrary format:

- workflows.data - the definitions of the workflows
- workflowInstances.data - instances of the workflows, each with its current status and its assignee
- employees.data - persons within the company eligible to be assigned to a workflow instance
- contractors.data - contractors from outside the company that may be involved and thus are
potential assignees

These files have to be read and analysed in an automated way, see below.

## Challenge
Please take some time and write some executable, tested code that you would
be delighted to see when written by your teammates that does the following:

- Reading the data files programmatically.
- Showing any inconsistent entries.
- Showing all workflows with their according instances.
- Showing all workflows having running instances and the number of those instances.
- Showing all contractors that are assignees to running instances.